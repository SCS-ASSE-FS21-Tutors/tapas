package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
public class AuctionHouseDiscoveryHttpAdapter implements ch.unisg.tapas.auctionhouse.application.port.out.AuctionHouseDiscoveryPort {

    @Override
    public List<AuctionHouseInformation> load(URI discoveryEndpoint){

        return null;
    }

}
