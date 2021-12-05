package ch.unisg.tapas.auctionhouse.adapter.in.messaging.websub;

import ch.unisg.tapas.auctionhouse.application.handler.AuctionStartedHandler;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    // This endpoint is for the hub to verify that out endpoint is active
    @GetMapping(path = "/websub-auction-started")
    public ResponseEntity verifyIntentWebSub(@RequestParam("hub.mode") String hubMode,
                                             @RequestParam("hub.topic") String hubTopic,
                                             @RequestParam("hub.challenge") String hubChallenge,
                                             @RequestParam("hub.lease_seconds") String hubLeaseSeconds
                                             ) {
        log.info("WebSub | Received verification of intent request from Hub: Topic URI "+ hubTopic);
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("hub.challenge", hubChallenge);

        org.springframework.http.HttpHeaders responseHeaders = new org.springframework.http.HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");

        return new ResponseEntity<>(jsonBody.toString(), responseHeaders, HttpStatus.OK);
    }

    // This endpoint is for when an auction is actually launched
    @PostMapping(path = "/websub-auction-started")
    public ResponseEntity auctionStartedWebSub(@RequestBody String payload) {
        log.info("WebSub | Received auction started event: "+ payload);
        // TODO: Implement
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
