package ch.unisg.tapas.auctionhouse.application.service;

import ch.unisg.tapas.auctionhouse.application.port.in.ProvideDiscoveredAuctionHousesCommand;
import ch.unisg.tapas.auctionhouse.application.port.in.ProvideDiscoveredAuctionHousesUseCase;
import ch.unisg.tapas.auctionhouse.domain.DiscoveredAuctionHouseInfo;
import ch.unisg.tapas.auctionhouse.domain.DiscoveredAuctionHouseRegistry;
import org.springframework.stereotype.Component;

@Component
public class ProvideDiscoveredAuctionHousesService implements ProvideDiscoveredAuctionHousesUseCase {

    @Override
    public DiscoveredAuctionHouseInfo[] provideDiscoveredAuctionHouses(ProvideDiscoveredAuctionHousesCommand command) {
        return DiscoveredAuctionHouseRegistry.getInstance()
            .getDiscoveredAuctionHouses()
            .toArray(new DiscoveredAuctionHouseInfo[0]);
    }
}
