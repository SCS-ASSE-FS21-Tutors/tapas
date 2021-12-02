package ch.unisg.tapas.auctionhouse.application.service;

import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;

import java.util.Collection;

public interface RetrieveAuctionHouseInformationQuery {
    Collection<AuctionHouseInformation> loadKnownAuctionHouses();
}
