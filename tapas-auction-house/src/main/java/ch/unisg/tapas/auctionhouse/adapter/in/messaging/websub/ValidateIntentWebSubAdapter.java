package ch.unisg.tapas.auctionhouse.adapter.in.messaging.websub;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class validates the subscription intent from the websub hub
 */
@RestController
public class ValidateIntentWebSubAdapter {

    @Value("${application.environment}")
    private String environment;

    @GetMapping(path = "/auction-started")
    public ResponseEntity<String> handleExecutorAddedEvent(@RequestParam("hub.challenge") String challenge) {



        // Different implementation depending on local development or production
        if (environment.equalsIgnoreCase("development")) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            String body = new JSONObject()
                .put("hub.challenge", challenge)
                .toString();
            return new ResponseEntity<>(body, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(challenge, HttpStatus.OK);
        }
    }
}
