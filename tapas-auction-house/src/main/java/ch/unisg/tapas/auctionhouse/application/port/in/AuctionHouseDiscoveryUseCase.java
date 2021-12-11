package ch.unisg.tapas.auctionhouse.application.port.in;

import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;

import java.net.URI;
import java.util.Collection;

public interface AuctionHouseDiscoveryUseCase {
    Collection<AuctionHouseInformation> discoverAuctionHouses(URI startingPoint);
}
