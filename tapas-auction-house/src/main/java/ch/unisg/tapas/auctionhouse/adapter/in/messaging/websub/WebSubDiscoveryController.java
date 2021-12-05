package ch.unisg.tapas.auctionhouse.adapter.in.messaging.websub;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebSubDiscoveryController {

    private final String auctionHouseUri;
    private final String hubUri;

   public WebSubDiscoveryController(@Value("${auction.house.uri}")String auctionHouseUri, @Value("${websub.hub}")String hubUri) {
       this.auctionHouseUri = auctionHouseUri;
       this.hubUri = hubUri;
    }


    @GetMapping(path = "/websub-discovery")
    public ResponseEntity getHubUri() {
        // This Endpoint returns the URI of our hub and the subscription endpoint
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Link",
            "<" + hubUri + ">; rel=\"hub\"");
        responseHeaders.add("Link",
            "<" + auctionHouseUri + "/websub-subscribe" + ">; rel=\"self\"");

        return ResponseEntity.ok()
            .headers(responseHeaders).build();
    }
}
