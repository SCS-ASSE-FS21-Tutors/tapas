package ch.unisg.tapas.auctionhouse.application.port.out;

import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;

import java.io.IOException;
import java.net.URI;
import java.util.List;

public interface AuctionHouseDiscoveryPort {
    List<AuctionHouseInformation> loadDiscoveryInfo(URI discoveryEndpoint) throws IOException, InterruptedException, Exception;
}
