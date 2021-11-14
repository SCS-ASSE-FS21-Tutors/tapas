package ch.unisg.tapas.auctionhouse.adapter.in.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.BidJsonRepresentation;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.dto.AuctionDto;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.dto.TaskDto;
import ch.unisg.tapas.auctionhouse.application.port.in.BidReceivedEvent;
import ch.unisg.tapas.auctionhouse.application.port.in.BidReceivedEventHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.LaunchAuctionCommand;
import ch.unisg.tapas.auctionhouse.application.port.in.LaunchAuctionUseCase;
import ch.unisg.tapas.auctionhouse.domain.Bid;
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
    private final BidReceivedEventHandler bidReceivedEventHandler;

    public UniformAuctionHouseApiController(BidReceivedEventHandler bidReceivedEventHandler) {
        this.bidReceivedEventHandler = bidReceivedEventHandler;
    }

    @PostMapping("/auction")
    public ResponseEntity newAuctionNotificationEndpoint(@RequestBody AuctionDto auctionDto) {

        if (auctionDto == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/bid", consumes = BidJsonRepresentation.MEDIA_TYPE)
    public ResponseEntity placeNewBidEndpoint(@RequestBody BidJsonRepresentation payload) {

        if (payload == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        try {
            Bid bid = BidJsonRepresentation.toBid(payload);
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

    @PostMapping("/taskwinner")
    public ResponseEntity taskwinnerNotificationEndpoint(@RequestBody TaskDto taskwinnerDto) {

        if (taskwinnerDto == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("/tasks/{taskId}")
    public TaskDto taskCompletionNotificationEndpoint(@PathVariable(value = "taskId") String taskId, @RequestBody TaskDto taskDto) {

        if (taskId == null || taskId.equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (taskDto == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return taskDto;
    }
}
