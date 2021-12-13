package ch.unisg.tapas.auctionhouse.application.port.in;

import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;

public interface StoreKnownAuctionHouseUseCase {
    void storeKnownAuctionHouse(StoreKnownAuctionHouseCommand storeKnownAuctionHouseCommand);
    void storeAuctionHouseToPropagate(StoreKnownAuctionHouseCommand storeKnownAuctionHouseCommand);
}
