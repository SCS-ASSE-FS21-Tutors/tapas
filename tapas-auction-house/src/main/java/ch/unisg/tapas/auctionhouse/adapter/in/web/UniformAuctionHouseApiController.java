package ch.unisg.tapas.auctionhouse.adapter.in.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.dto.AuctionDto;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.dto.BidDto;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.dto.TaskDto;
import ch.unisg.tapas.auctionhouse.application.port.in.LaunchAuctionCommand;
import ch.unisg.tapas.auctionhouse.application.port.in.LaunchAuctionUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller that handles HTTP requests for launching auctions. This controller implements the
 * {@link LaunchAuctionUseCase} use case using the {@link LaunchAuctionCommand}.
 */
@RestController
public class UniformAuctionHouseApiController {

    @PostMapping("/auction")
    public ResponseEntity newAuctionNotificationEndpoint(@RequestBody AuctionDto auctionDto){

        if(auctionDto == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("/bid")
    public ResponseEntity placeNewBidEndpoint(@RequestBody BidDto bidDto){
        System.out.println("Bid received");

        if(bidDto == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/taskwinner")
    public ResponseEntity taskwinnerNotificationEndpoint(@RequestBody TaskDto taskwinnerDto){

        if(taskwinnerDto == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("/tasks/{taskId}")
    public TaskDto taskCompletionNotificationEndpoint(@PathVariable(value="taskId") String taskId, @RequestBody TaskDto taskDto){

        if(taskId == null || taskId.equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if(taskDto == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return taskDto;
    }
}
