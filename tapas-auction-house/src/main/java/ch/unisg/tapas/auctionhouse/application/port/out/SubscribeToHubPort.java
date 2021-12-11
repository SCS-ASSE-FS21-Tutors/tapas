package ch.unisg.tapas.auctionhouse.application.port.out;

import java.net.URI;

public interface SubscribeToHubPort {
    void subscribeToHub(URI hubUri, URI topicUri);
}
