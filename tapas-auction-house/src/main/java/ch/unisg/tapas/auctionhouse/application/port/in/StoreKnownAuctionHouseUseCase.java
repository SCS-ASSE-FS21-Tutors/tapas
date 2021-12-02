package ch.unisg.tapas.auctionhouse.application.port.in;

import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;

public interface StoreKnownAuctionHouseUseCase {
    void storeKnownAuctionHouse(AuctionHouseInformation auctionHouseInformation);
}
