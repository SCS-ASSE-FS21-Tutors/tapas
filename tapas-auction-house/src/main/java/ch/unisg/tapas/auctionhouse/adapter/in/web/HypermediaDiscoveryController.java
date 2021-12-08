package ch.unisg.tapas.auctionhouse.adapter.in.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionHouseDiscoveryRepresentation;
import ch.unisg.tapas.auctionhouse.application.port.in.AuctionHouseDiscoveryUseCase;
import ch.unisg.tapas.auctionhouse.application.port.in.RetrieveAuctionHouseInformationQuery;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class HypermediaDiscoveryController {

    @Autowired
    private RetrieveAuctionHouseInformationQuery retrieveAuctionHouseInformationQuery;

    @Autowired
    private AuctionHouseDiscoveryUseCase auctionHouseDiscoveryUseCase;

    @GetMapping(path = "/discovery/")
    public AuctionHouseDiscoveryRepresentation getAuctionHouseDiscovery() {

        var result = retrieveAuctionHouseInformationQuery.loadKnownAuctionHouses();
        return new AuctionHouseDiscoveryRepresentation(result);
    }

    @GetMapping(path = "/discovery/crawl")
    @Parameter(description  = "URI of of the auction house where the crawling should start" ,name="startUri", required = true, example = "https://tapas-auction-house.86-119-34-242.nip.io")
    public AuctionHouseDiscoveryRepresentation crawl(@RequestParam("startUri") URI startUri){

        var info = auctionHouseDiscoveryUseCase.discoverAuctionHouses(startUri);
        return new AuctionHouseDiscoveryRepresentation(info);
    }
}
