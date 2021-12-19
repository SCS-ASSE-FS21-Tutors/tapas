package ch.unisg.tapas.auctionhouse.adapter.in.messaging.websub;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.handler.AuctionStartedHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.AuctionStartedEvent;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;


/**
 * This class is a template for handling auction started events received via WebSub
 */
@RestController
@Log4j2
public class AuctionStartedEventListenerWebSubAdapter {
    private final AuctionStartedHandler auctionStartedHandler;

    public AuctionStartedEventListenerWebSubAdapter(AuctionStartedHandler auctionStartedHandler) {
        this.auctionStartedHandler = auctionStartedHandler;
    }

    @GetMapping(path = "/websub-auction-started")
    @Operation(summary = "This endpoint is for the hub to verify that out endpoint is active")
    public ResponseEntity verifyIntentWebSub(@RequestParam("hub.mode") String hubMode,
                                             @RequestParam("hub.topic") String hubTopic,
                                             @RequestParam("hub.challenge") String hubChallenge,
                                             @RequestParam("hub.lease_seconds") String hubLeaseSeconds
    ) {
        log.info("WebSub | Received verification of intent request from Hub: Topic URI " + hubTopic);

        org.springframework.http.HttpHeaders responseHeaders = new org.springframework.http.HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, "text/plain;charset=UTF-8");

        return new ResponseEntity<>(hubChallenge, responseHeaders, HttpStatus.OK);
    }

    @PostMapping(path = "/websub-auction-started", consumes = "*/*")
    @Operation(summary = "This endpoint is for when an auction is actually launched")
    public ResponseEntity auctionStartedWebSub(@RequestBody String payload) {
        JSONArray jsonArray = new JSONArray(payload);
        try {
            // Get the first auction from the array.
            // (Depending on the business requirements this should be changed)
            Auction auction = AuctionJsonRepresentation.deserialize(jsonArray.get(0).toString());
            log.info("WebSub | Received auction started event for task type {} from auction house {}",
                auction.getTaskType().getValue(), auction.getAuctionHouseUri().getValue());
            AuctionStartedEvent auctionStartedEvent = new AuctionStartedEvent(auction);
            auctionStartedHandler.handleAuctionStartedEvent(auctionStartedEvent);
        } catch (NullPointerException e) {
            log.error("WebSub | Received empty auction array from hub");
        } catch (Exception e){
            log.error("WebSub | Something went wrong while parsing and handling the received auction");
        }
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
