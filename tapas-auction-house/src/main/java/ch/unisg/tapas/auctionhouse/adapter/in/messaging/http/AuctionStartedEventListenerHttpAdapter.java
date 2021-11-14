package ch.unisg.tapas.auctionhouse.adapter.in.messaging.http;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.handler.AuctionStartedHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.AuctionStartedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuctionStartedEventListenerHttpAdapter {
    @PostMapping(path = "/auction",  consumes = {AuctionJsonRepresentation.MEDIA_TYPE})
    public ResponseEntity<String> handleAuctionStartedEvent(@RequestBody AuctionJsonRepresentation auctionRepresentation) {
        var event = new AuctionStartedEvent(auctionRepresentation);
        var handler = new AuctionStartedHandler();
        var ok = handler.handleAuctionStartedEvent(event);
        return new ResponseEntity<>(ok ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
