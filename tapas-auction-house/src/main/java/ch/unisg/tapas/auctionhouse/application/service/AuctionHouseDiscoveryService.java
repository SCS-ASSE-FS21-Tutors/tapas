package ch.unisg.tapas.auctionhouse.application.service;

import ch.unisg.tapas.auctionhouse.application.port.in.AuctionHouseDiscoveryUseCase;
import ch.unisg.tapas.auctionhouse.application.port.out.AuctionHouseDiscoveryPort;
import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Component
public class AuctionHouseDiscoveryService implements AuctionHouseDiscoveryUseCase {

    private AuctionHouseDiscoveryPort discoveryPort;
    private final int recursionLimit;

    @Autowired
    public AuctionHouseDiscoveryService(AuctionHouseDiscoveryPort discoveryPort) {
        this.discoveryPort = discoveryPort;
        recursionLimit = 3;
    }

    @Override
    public Collection<AuctionHouseInformation> discoverAuctionHouses(URI startingPoint){

        HashMap<URI, AuctionHouseInformation> knownAuctionHouses = new HashMap<>();
        discoverAuctionHousesRecursively(startingPoint, 1, knownAuctionHouses, new HashSet<>());
        return knownAuctionHouses.values();
    }

    private void discoverAuctionHousesRecursively(URI auctionHouseUri, int recursionLevel, Map<URI, AuctionHouseInformation> knownAuctionHouses, Set<URI> visitedUris){

        // If we go too deep return
        if(recursionLevel > recursionLimit) return;

        // If the auction house is already known return
        if(visitedUris.contains(auctionHouseUri)) return;

        // Load auction house information
        List<AuctionHouseInformation> loadedAuctionHouses = loadAuctionHouseInformationFailsafe(auctionHouseUri);
        visitedUris.add(auctionHouseUri);

        // Store info
        for(var auctionHouseInfo : loadedAuctionHouses){

            knownAuctionHouses.putIfAbsent(auctionHouseInfo.getAuctionhouseuri(), auctionHouseInfo);

            // If the auction house references itself this has priority over the info from another auction house
            if(auctionHouseUri.equals(auctionHouseInfo.getAuctionhouseuri()))
                knownAuctionHouses.put(auctionHouseInfo.getAuctionhouseuri(), auctionHouseInfo);
        }

        // Recursively load new auction houses
        for(var auctionHouseInfo : loadedAuctionHouses) {

            discoverAuctionHousesRecursively(
                auctionHouseInfo.getAuctionhouseuri(),
                recursionLevel + 1,
                knownAuctionHouses,
                visitedUris);
        }
    }

    /**
     * Loads auction house info via the port but should never fail
     * In case of failure an empty list is returned
     * @param auctionHouseUri
     * @return
     */
    private List<AuctionHouseInformation> loadAuctionHouseInformationFailsafe(URI auctionHouseUri) {

        log.debug("Loading auction house information from: " + auctionHouseUri.toString());

        try{

            // load auction houses
            var loadedAuctionHouses = discoveryPort.loadDiscoveryInfo(auctionHouseUri);
            // filter our invalid values
            var validAuctionHouses = loadedAuctionHouses.stream().filter(this::isAcceptableInfo).collect(Collectors.toList());

            log.info("Auction house from " + auctionHouseUri.toString() +
                " knows about the following auction houses: " + validAuctionHouses.stream().map(i->i.getAuctionhouseuri().toString()).collect(Collectors.joining(" ")));
            return validAuctionHouses;

        } catch (Exception ex){

            // Fail silently
            log.info("Loading auction house information from " + auctionHouseUri.toString() + " resulted in error: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Checks if we can accept a given auction house information
     * @param info
     * @return
     */
    private boolean isAcceptableInfo(AuctionHouseInformation info){

        if(info.getAuctionhouseuri() == null)
            return false;

        if(info.getGroupName() == null)
            return false;

        if(info.getTaskTypes() == null)
            return false;

        return true;
    }
}
