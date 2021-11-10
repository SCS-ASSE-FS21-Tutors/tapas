package ch.unisg.tapas.auctionhouse.adapter.in.messaging.websub;

import ch.unisg.tapas.auctionhouse.application.handler.AuctionStartedHandler;

import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class is a template for handling auction started events received via WebSub
 */
@RestController
public class AuctionStartedEventListenerWebSubAdapter {
    private final AuctionStartedHandler auctionStartedHandler;

    public AuctionStartedEventListenerWebSubAdapter(AuctionStartedHandler auctionStartedHandler) {
        this.auctionStartedHandler = auctionStartedHandler;
    }
    /**
    *   Controller which listens to auction-started callbacks
    *   @return 200 OK
    **/
    @PostMapping(path = "/auction-started")
    public ResponseEntity<Void> handleExecutorAddedEvent(@RequestBody String payload) {

        // Payload should be a JSONArray with auctions
        JSONArray jsonArray = new JSONArray(payload);
        for (Object auction : jsonArray) {
            System.out.println(auction);
            // TODO logic to call handleAuctionStartedEvent()
            // auctionStartedHandler.handleAuctionStartedEvent(auctionStartedEvent)
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
