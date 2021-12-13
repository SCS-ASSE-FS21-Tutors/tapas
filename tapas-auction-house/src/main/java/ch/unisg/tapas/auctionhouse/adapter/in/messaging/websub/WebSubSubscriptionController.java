package ch.unisg.tapas.auctionhouse.adapter.in.messaging.websub;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSubSubscriptionController {

    @PostMapping(path = "/websub-subscribe", consumes = "application/x-www-form-urlencoded")
    @Operation(summary = "allow the other groups to subscribe to our auctions")
    public ResponseEntity subscribeToWebSub() {

        // TODO Implement
        // This endpoint should allow the other groups to subscribe to our auctions

        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }
}
