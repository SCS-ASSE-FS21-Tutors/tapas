package ch.unisg.tapas.auctionhouse.application.service;

import ch.unisg.tapas.auctionhouse.application.port.in.StoreKnownAuctionHouseCommand;
import ch.unisg.tapas.auctionhouse.application.port.in.StoreKnownAuctionHouseUseCase;
import ch.unisg.tapas.auctionhouse.application.port.in.RetrieveAuctionHouseInformationQuery;
import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;
import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformationRegistry;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AuctionHouseInformationService implements RetrieveAuctionHouseInformationQuery, StoreKnownAuctionHouseUseCase {

    private AuctionHouseInformationRegistry registry = new AuctionHouseInformationRegistry();
    private AuctionHouseInformationRegistry registryPropagating = new AuctionHouseInformationRegistry();

    @Override
    public void storeKnownAuctionHouse(StoreKnownAuctionHouseCommand storeKnownAuctionHouseCommand){

        registry.addAuctionHouseInformation(storeKnownAuctionHouseCommand.getAuctionHouseInformation());
    }

    public void storeAuctionHouseToPropagate(StoreKnownAuctionHouseCommand storeKnownAuctionHouseCommand){
        registryPropagating.addAuctionHouseInformation(storeKnownAuctionHouseCommand.getAuctionHouseInformation());
    }

    @Override
    public Collection<AuctionHouseInformation> loadKnownAuctionHouses(){

        return registry.getAuctionHouses();
    }

    public Collection<AuctionHouseInformation> loadPropagatedAuctionHouses(){

        return registryPropagating.getAuctionHouses();
    }
}
