package ch.unisg.tapas.auctionhouse.adapter.in.messaging.websub;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.handler.AuctionStartedHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.AuctionStartedEvent;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.Auction.AuctionDeadline;
import ch.unisg.tapas.auctionhouse.domain.Auction.AuctionHouseUri;
import ch.unisg.tapas.auctionhouse.domain.Auction.AuctionId;
import ch.unisg.tapas.auctionhouse.domain.Auction.AuctionedTaskType;
import ch.unisg.tapas.auctionhouse.domain.Auction.AuctionedTaskUri;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

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
     * @throws URISyntaxException
    **/
    @PostMapping(path = "/auction-started")
    public ResponseEntity<Void> handleExecutorAddedEvent(@RequestBody Collection<AuctionJsonRepresentation> payload) throws URISyntaxException {

        for (AuctionJsonRepresentation auction : payload) {
            auctionStartedHandler.handleAuctionStartedEvent(
                new AuctionStartedEvent(
                    new Auction(new AuctionId(auction.getAuctionId()),
                    new AuctionHouseUri(new URI(auction.getAuctionHouseUri())),
                    new AuctionedTaskUri(new URI(auction.getTaskUri())),
                    new AuctionedTaskType(auction.getTaskType()),
                    new AuctionDeadline(auction.getDeadline()))
                ));
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
