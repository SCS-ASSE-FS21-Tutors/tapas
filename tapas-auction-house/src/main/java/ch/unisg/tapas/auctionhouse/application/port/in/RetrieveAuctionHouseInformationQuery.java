package ch.unisg.tapas.auctionhouse.application.port.in;

import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;

import java.util.Collection;

public interface RetrieveAuctionHouseInformationQuery {
    Collection<AuctionHouseInformation> loadKnownAuctionHouses();
}
