package ch.unisg.tapas.auctionhouse.adapter.in.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionJsonRepresentation;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.BidJsonRepresentation;
import ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt.BidReceivedEventListenerMqttAdapter;
import ch.unisg.tapas.auctionhouse.application.handler.BidReceivedHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.BidReceivedEvent;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.Bid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

// TODO Fix structure due to MQTT
@RestController
public class AddBidWebController {
    private static final Logger LOGGER = LogManager.getLogger(AddBidWebController.class);

    @PostMapping(path = "/bid", consumes = BidJsonRepresentation.MEDIA_TYPE)
    public ResponseEntity<String> addBid(@RequestBody BidJsonRepresentation payload) {
        BidReceivedEvent bidReceivedEvent = new BidReceivedEvent(new Bid(
            new Auction.AuctionId(payload.getAuctionId()),
            new Bid.BidderName(payload.getBidderName()),
            new Bid.BidderAuctionHouseUri(URI.create(payload.getBidderAuctionHouseUri())),
            new Bid.BidderTaskListUri(URI.create(payload.getBidderTaskListUri()))
        ));

        LOGGER.info("Bid received:" + payload);

        BidReceivedHandler bidReceivedHandler = new BidReceivedHandler();
        bidReceivedHandler.handleNewBidReceivedEvent(bidReceivedEvent);

        HttpHeaders responseHeaders = new HttpHeaders();

        return new ResponseEntity<>(responseHeaders, HttpStatus.NO_CONTENT);
    }

}
