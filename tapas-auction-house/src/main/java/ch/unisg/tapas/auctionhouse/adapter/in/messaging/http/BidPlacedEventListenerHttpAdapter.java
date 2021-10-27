package ch.unisg.tapas.auctionhouse.adapter.in.messaging.http;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.BidJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.handler.BidPlacedHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.BidPlacedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BidPlacedEventListenerHttpAdapter {
    @PostMapping(path = "/bid/{AuctionId}",  consumes = {BidJsonRepresentation.MEDIA_TYPE})
    public ResponseEntity<String> handleBidPlacedEvent(
        @PathVariable("AuctionId") String auctionId,
        @RequestBody BidJsonRepresentation bidRepresentation)
    {
        var bid = bidRepresentation.deserialize();
        var event = new BidPlacedEvent(bid);
        var handler = new BidPlacedHandler();
        var ok = handler.handleBidPlacedEvent(event);
        return new ResponseEntity<>(ok ? HttpStatus.ACCEPTED : HttpStatus.NO_CONTENT);
    }
}
