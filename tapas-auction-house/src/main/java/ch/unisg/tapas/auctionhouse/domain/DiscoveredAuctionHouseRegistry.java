package ch.unisg.tapas.auctionhouse.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DiscoveredAuctionHouseRegistry {

    private static final Logger LOGGER = LogManager.getLogger(DiscoveredAuctionHouseRegistry.class);

    private Set<DiscoveredAuctionHouseInfo> discoveredAuctionHouses = new HashSet<>();

    private static DiscoveredAuctionHouseRegistry instance = new DiscoveredAuctionHouseRegistry();

    private DiscoveredAuctionHouseRegistry() { }

    public static DiscoveredAuctionHouseRegistry getInstance() {
        return instance;
    }

    public void addDiscoveredAuctionHouse(DiscoveredAuctionHouseInfo info) {

        var added = discoveredAuctionHouses.add(info);
        if (added) {
            LOGGER.info("Added new discovered Auction House: " + info);
            LOGGER.info("Number of discovered Auction Houses: " + discoveredAuctionHouses.size());
        }
    }

    public Set<DiscoveredAuctionHouseInfo> getDiscoveredAuctionHouses() {
        return discoveredAuctionHouses;
    }

    public void clear() {
        discoveredAuctionHouses.clear();
    }

    public void addAllDiscoveredAuctionHouses(DiscoveredAuctionHouseInfo[] infos) {
        discoveredAuctionHouses.addAll(Arrays.asList(infos));
    }
}
