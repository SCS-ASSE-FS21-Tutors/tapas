package ch.unisg.tapas.auctionhouse.application.port.out;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

public interface DiscoverHubPort {
    Map<String, Optional<String>> discoverHub(URI auctionHouseUri);
}
