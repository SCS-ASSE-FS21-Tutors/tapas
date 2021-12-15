package ch.unisg.tapas.auctionhouse.adapter.in.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.BidJsonRepresentation;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.TaskJsonRepresentation;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.dto.AuctionDto;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.dto.TaskDto;
import ch.unisg.tapas.auctionhouse.application.port.in.*;
import ch.unisg.tapas.auctionhouse.domain.Bid;
import ch.unisg.tapas.auctionhouse.domain.Task;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


/**
 * Controller that handles HTTP requests for launching auctions. This controller implements the
 * {@link LaunchAuctionUseCase} use case using the {@link LaunchAuctionCommand}.
 */
@Log4j2
@RestController
public class UniformAuctionHouseApiController {
    private final ProcessExternalTaskUseCase processExternalTaskUseCase;
    private final BidReceivedEventHandler bidReceivedEventHandler;

    public UniformAuctionHouseApiController(ProcessExternalTaskUseCase processExternalTaskUseCase, BidReceivedEventHandler bidReceivedEventHandler) {
        this.processExternalTaskUseCase = processExternalTaskUseCase;
        this.bidReceivedEventHandler = bidReceivedEventHandler;
    }

    @PostMapping("/auction")
    @Operation(summary = "Inform the auction house about a new auction")
    public ResponseEntity newAuctionNotificationEndpoint(@RequestBody AuctionDto auctionDto) {

        if (auctionDto == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/bid", consumes = BidJsonRepresentation.MEDIA_TYPE)
    @Operation(summary = "Place a new bid on a open auction")
    public ResponseEntity placeNewBidEndpoint(@RequestBody BidJsonRepresentation payload) {
        if (payload == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        try {
            Bid bid = BidJsonRepresentation.toBid(payload);
            log.info("Bid received from {}", bid.getBidderName().toString());
            BidReceivedEvent bidReceivedEvent = new BidReceivedEvent(bid);
            boolean received = bidReceivedEventHandler.handleBidReceivedEvent(bidReceivedEvent);
            // Check if bid could successfully be stored
            if (received) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path="/taskwinner", consumes = TaskJsonRepresentation.MEDIA_TYPE)
    @Operation(summary = "Inform the auction house that we won a bid")
    public ResponseEntity taskwinnerNotificationEndpoint(@RequestBody TaskJsonRepresentation payload) {
        log.info("Task winner notification received");
        if (payload == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            Task task = TaskJsonRepresentation.toTask(payload);
            ProcessExternalTaskCommand command = new ProcessExternalTaskCommand(task);
            boolean assigned = processExternalTaskUseCase.processExternalTask(command);
            if(assigned){
                return new ResponseEntity(HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/tasks/{taskId}")
    @Operation(summary = "Update task after it was executed")
    public TaskDto taskCompletionNotificationEndpoint(@PathVariable(value = "taskId") String taskId, @RequestBody TaskDto taskDto) {

        if (taskId == null || taskId.equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (taskDto == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return taskDto;
    }
}
