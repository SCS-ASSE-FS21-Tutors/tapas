package ch.unisg.tapas.auctionhouse.adapter.in.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionHouseDiscoveryRepresentation;
import ch.unisg.tapas.auctionhouse.application.port.in.RetrieveAuctionHouseInformationQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HypermediaDiscoveryController {

    @Autowired
    private RetrieveAuctionHouseInformationQuery retrieveAuctionHouseInformationQuery;

    @GetMapping(path = "/discovery/")
    public AuctionHouseDiscoveryRepresentation getAuctionHouseDiscovery() {

        var result = retrieveAuctionHouseInformationQuery.loadKnownAuctionHouses();
        return new AuctionHouseDiscoveryRepresentation(result);
    }
}
