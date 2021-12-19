package ch.unisg.tapas.auctionhouse.adapter.in.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.TaskJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.port.in.LaunchAuctionCommand;
import ch.unisg.tapas.auctionhouse.application.port.in.LaunchAuctionUseCase;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.Task;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@Log4j2
@RequestMapping("/internal")
public class InternalController {
    private final LaunchAuctionUseCase launchAuctionUseCase;

    @Value("${tasks.list.uri}")
    private String taskListUri;

    public InternalController(LaunchAuctionUseCase launchAuctionUseCase) {
        this.launchAuctionUseCase = launchAuctionUseCase;
    }


    @Operation(summary = "Should only be called by services of the TAPAS 3 group. Creates a new auction for a service that cannot be executed internally")
    @PostMapping(path = "/create-auction-for-task/", consumes = TaskJsonRepresentation.MEDIA_TYPE)
    public ResponseEntity createAuctionForTask(@RequestBody TaskJsonRepresentation taskJsonRepresentation) {
        Task task = TaskJsonRepresentation.toTask(taskJsonRepresentation);
        log.info("Received launch auction request for task {} of type {}",taskJsonRepresentation.getTaskId(), taskJsonRepresentation.getTaskType());

        LaunchAuctionCommand command = new LaunchAuctionCommand(
            new Auction.AuctionedTaskUri(URI.create(taskListUri + "/tasks/" + task.getTaskId().getValue())),
            new Auction.AuctionedTaskType(task.getTaskType().getValue()),
            null
        );

        launchAuctionUseCase.launchAuction(command);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
