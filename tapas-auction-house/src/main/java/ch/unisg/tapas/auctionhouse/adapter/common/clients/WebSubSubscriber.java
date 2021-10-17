package ch.unisg.tapas.auctionhouse.adapter.common.clients;

import java.net.URI;

/**
 * Subscribes to the WebSub hubs of auction houses discovered at run time. This class is instantiated
 * from {@link ch.unisg.tapas.TapasAuctionHouseApplication} when boostraping the TAPAS marketplace
 * via WebSub.
 */
public class WebSubSubscriber {

    public void subscribeToAuctionHouseEndpoint(URI endpoint) {
        // TODO Subscribe to the auction house endpoint via WebSub:
        // 1. Send a request to the auction house in order to discover the WebSub hub to subscribe to.
        // The request URI should depend on the design of the Auction House HTTP API.
        // 2. Send a subscription request to the discovered WebSub hub to subscribe to events relevant
        // for this auction house.
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
