package ch.unisg.tapas.auctionhouse.adapter.common.clients;

import ch.unisg.tapas.auctionhouse.application.port.out.DiscoverHubPort;
import ch.unisg.tapas.auctionhouse.application.port.out.SubscribeToHubPort;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Map;
import java.util.Optional;


@Component
@Log4j2
public class WebSubSubscriber {

    private final DiscoverHubPort discoverHubPort;
    private final SubscribeToHubPort subscribeToHubPort;

    public WebSubSubscriber(DiscoverHubPort discoverHubPort, SubscribeToHubPort subscribeToHubPort){
        this.discoverHubPort = discoverHubPort;
        this.subscribeToHubPort = subscribeToHubPort;
    }

    public void subscribeToAuctionHouseEndpoint(URI endpoint) {
        // TODO Subscribe to the auction house endpoint via WebSub:
        // 1. Send a request to the auction house in order to discover the WebSub hub to subscribe to.
        // The request URI should depend on the design of the Auction House HTTP API.
        Map<String, Optional<String>> uris = discoverHubPort.discoverHub(endpoint);

        // 2. Send a subscription request to the discovered WebSub hub to subscribe to events relevant
        // for this auction house.
        Optional<String> hubUri = uris.get("hub");
        Optional<String> selfUri = uris.get("self");
        if(hubUri.isPresent() && selfUri.isPresent()){
            subscribeToHubPort.subscribeToHub(URI.create(hubUri.get()),URI.create(selfUri.get()));
        } else {
            log.warn("WebSub | Could not retrieve WebSub Hub from: " + endpoint.toString());
        }
        // 3. Handle the validation of intent from the WebSub hub (see WebSub protocol).
        //
        // Once the subscription is activated, the hub will send "fat pings" with content updates.
        // The content received from the hub will depend primarily on the design of the Auction House
        // HTTP API.
        //
        // For further details see:
        // - W3C WebSub Recommendation: https://www.w3.org/TR/websub/
        // - the implementation notes of the WebSub hub you are using to distribute events
    }
}
