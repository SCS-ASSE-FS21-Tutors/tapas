package ch.unisg.tapas.auctionhouse.adapter.in.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionHouseDiscoveryRepresentation;
import ch.unisg.tapas.auctionhouse.application.port.in.AuctionHouseDiscoveryUseCase;
import ch.unisg.tapas.auctionhouse.application.port.in.RetrieveAuctionHouseInformationQuery;
import ch.unisg.tapas.auctionhouse.application.port.in.StoreKnownAuctionHouseCommand;
import ch.unisg.tapas.auctionhouse.application.port.in.StoreKnownAuctionHouseUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@Log4j2
public class HypermediaDiscoveryController {

    @Autowired
    private RetrieveAuctionHouseInformationQuery retrieveAuctionHouseInformationQuery;

    @Autowired
    private AuctionHouseDiscoveryUseCase auctionHouseDiscoveryUseCase;

    @GetMapping(path = "/discovery/")
    @Operation(summary = "Retrieves the auction houses that are propagated. Others should use this endpoint to crawl")
    public AuctionHouseDiscoveryRepresentation getAuctionHouseDiscovery() {
        log.info("Received discovery request for crawling");
        var result = retrieveAuctionHouseInformationQuery.loadPropagatedAuctionHouses();
        return new AuctionHouseDiscoveryRepresentation(result);
    }

    @GetMapping(path = "/discovery/allKnown")
    @Operation(summary = "Retrieves all known auction houses")
    public AuctionHouseDiscoveryRepresentation getAuctionHouseDiscoveryAll() {
        log.info("Received discovery request for all known auction houses");
        var result = retrieveAuctionHouseInformationQuery.loadKnownAuctionHouses();
        return new AuctionHouseDiscoveryRepresentation(result);
    }

    @GetMapping(path = "/discovery/crawl")
    @Operation(summary = "Starts crawling auction houses with a given start url")
    @Parameter(description  = "URI of of the auction house where the crawling should start" ,name="startUri", required = true, example = "http://localhost:8085")
    public AuctionHouseDiscoveryRepresentation crawl(@RequestParam("startUri") URI startUri){
        log.info("Received discovery crawling request with start URI " + startUri.toString());
        var info = auctionHouseDiscoveryUseCase.discoverAuctionHouses(startUri);
        return new AuctionHouseDiscoveryRepresentation(info);
    }
}
