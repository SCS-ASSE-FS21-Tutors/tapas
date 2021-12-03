package ch.unisg.tapas.auctionhouse.adapter.common.formats;

import ch.unisg.tapas.auctionhouse.domain.DiscoveredAuctionHouseInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DiscoveredAuctionHousesRepresentation {

    public final static String MEDIA_TYPE = "application/auctionhousediscovery+json";

    public static DiscoveredAuctionHouseInfo[] deserialize(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, DiscoveredAuctionHouseInfo[].class);
    }

    public static String serialize(DiscoveredAuctionHouseInfo[] discoveredAuctionHouses) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        var json = mapper.writeValueAsString(discoveredAuctionHouses);
        return json;
    }
}
