package ch.unisg.tapas.auctionhouse.adapter.common.formats;

import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.Bid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

public class BidJsonRepresentation {
    public static final String MEDIA_TYPE = "application/bid+json";

    @Getter
    @Setter
    private String auctionId;

    @Getter
    @Setter
    private String bidderName;

    @Getter
    @Setter
    private String bidderAuctionHouseUri;

    @Getter
    @Setter
    private String bidderTaskListUri;


    public BidJsonRepresentation(String auctionId, String bidderName, String bidderAuctionHouseUri,
                                 String bidderTaskListUri) {
        this.auctionId = auctionId;
        this.bidderName = bidderName;
        this.bidderAuctionHouseUri = bidderAuctionHouseUri;
        this.bidderTaskListUri = bidderTaskListUri;
    }

    public BidJsonRepresentation(Bid bid) {
        this.auctionId = bid.getAuctionId().getValue();
        this.bidderName = bid.getBidderName().getValue();
        this.bidderAuctionHouseUri = bid.getBidderAuctionHouseUri().getValue().toString();
        this.bidderTaskListUri = bid.getBidderTaskListUri().getValue().toString();
    }

    public static String serialize(Bid bid) throws JsonProcessingException {
        BidJsonRepresentation representation = new BidJsonRepresentation(bid);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(representation);
    }

    public static Bid deserialize(String bidJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(bidJson);

        Auction.AuctionId auctionId = new Auction.AuctionId(jsonNode.get("auctionId").asText());
        Bid.BidderName bidderName = new Bid.BidderName(jsonNode.get("bidderName").asText());
        Bid.BidderAuctionHouseUri bidderAuctionHouseUri = new Bid.BidderAuctionHouseUri(URI.create(jsonNode.get("bidderAuctionHouseUri").asText()));
        Bid.BidderTaskListUri bidderTaskListUri = new Bid.BidderTaskListUri(URI.create(jsonNode.get("bidderTaskListUri").asText()));

        Bid bid = new Bid(auctionId, bidderName, bidderAuctionHouseUri, bidderTaskListUri);
        return bid;
    }

    public static Bid toBid(BidJsonRepresentation bidJsonRepresentation) {
        Bid bid = new Bid(
            new Auction.AuctionId(bidJsonRepresentation.getAuctionId()),
            new Bid.BidderName(bidJsonRepresentation.getBidderName()),
            new Bid.BidderAuctionHouseUri(URI.create(bidJsonRepresentation.getBidderAuctionHouseUri())),
            new Bid.BidderTaskListUri(URI.create(bidJsonRepresentation.getBidderTaskListUri()))
        );
        return bid;
    }

}
