package ch.unisg.tapas.auctionhouse.application.port.out;

import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;

import java.net.URI;
import java.util.List;

public interface AuctionHouseDiscoveryPort {
    List<AuctionHouseInformation> load(URI discoveryEndpoint);
}
