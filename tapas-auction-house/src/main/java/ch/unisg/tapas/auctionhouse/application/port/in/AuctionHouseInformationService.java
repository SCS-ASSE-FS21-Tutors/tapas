package ch.unisg.tapas.auctionhouse.application.port.in;

import ch.unisg.tapas.auctionhouse.application.service.RetrieveAuctionHouseInformationQuery;
import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;
import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformationRegistry;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AuctionHouseInformationService implements RetrieveAuctionHouseInformationQuery, StoreKnownAuctionHouseUseCase {

    private AuctionHouseInformationRegistry registry = new AuctionHouseInformationRegistry();

    @Override
    public void storeKnownAuctionHouse(AuctionHouseInformation auctionHouseInformation){

        registry.addAuctionHouseInformation(auctionHouseInformation);
    }

    @Override
    public Collection<AuctionHouseInformation> loadKnownAuctionHouses(){

        return registry.getAuctionHouses();
    }
}
