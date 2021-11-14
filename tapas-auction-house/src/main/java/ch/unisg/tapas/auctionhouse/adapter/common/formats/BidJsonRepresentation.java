package ch.unisg.tapas.auctionhouse.adapter.common.formats;

import ch.unisg.tapas.auctionhouse.domain.Bid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

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

}
