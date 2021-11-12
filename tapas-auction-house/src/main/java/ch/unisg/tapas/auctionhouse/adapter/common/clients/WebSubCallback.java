package ch.unisg.tapas.auctionhouse.adapter.common.clients;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class WebSubCallback {

    private static final Logger LOGGER = LogManager.getLogger(WebSubCallback.class);

    private final WebSubConfig webSubConfig;

    @GetMapping(path = "/subscribe")
    public ResponseEntity<String> handleCallback(
        @RequestParam("hub.challenge") Optional<String> challengeOptional,
        @RequestBody Optional<String> bodyOptional) {

        if (challengeOptional.isPresent()) {
            LOGGER.info("Verifying WebSub Subscription Intent");

            var challenge = challengeOptional.get();

            if (webSubConfig.isProductionEnvironment()) {
                return new ResponseEntity<>(challenge, HttpStatus.OK);
            } else {
                var json = new JSONObject().put("hub.challenge", challenge).toString();
                var headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                return new ResponseEntity<>(json, headers, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/subscribe")
    public ResponseEntity<String> handleCallback(
        @RequestBody Optional<String> bodyOptional) {
        LOGGER.info("WebSub callback triggered");
        bodyOptional.ifPresent(System.out::println);

        // TODO: Propagate new auctions to listener

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
