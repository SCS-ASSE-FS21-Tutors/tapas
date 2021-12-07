package ch.unisg.tapas.auctionhouse.domain;

import java.util.Collection;
import java.util.HashMap;

public class AuctionHouseInformationRegistry {

    private HashMap<String, AuctionHouseInformation> registry = new HashMap<>();

    public void addAuctionHouseInformation(AuctionHouseInformation auctionHouseInformation){

        registry.put(auctionHouseInformation.getAuctionhouseuri().toString(), auctionHouseInformation);
    }

    public Collection<AuctionHouseInformation> getAuctionHouses(){

        return registry.values();
    }
}
