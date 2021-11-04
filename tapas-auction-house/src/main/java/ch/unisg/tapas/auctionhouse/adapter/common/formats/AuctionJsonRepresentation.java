package ch.unisg.tapas.auctionhouse.adapter.common.formats;

import ch.unisg.tapas.auctionhouse.domain.Auction;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

/**
 * Used to expose a representation of the state of an auction through an interface. This class is
 * only meant as a starting point when defining a uniform HTTP API for the Auction House: feel free
 * to modify this class as you see fit!
 */
public class AuctionJsonRepresentation {
    public static final String MEDIA_TYPE = "application/json";

    @Getter @Setter
    private String auctionId;

    @Getter @Setter
    private String auctionHouseUri;

    @Getter @Setter
    private String taskUri;

    @Getter @Setter
    private String taskType;

    @Getter @Setter
    private String deadline;

    public AuctionJsonRepresentation() {  }

    public AuctionJsonRepresentation(String auctionId, String auctionHouseUri, String taskUri,
            String taskType, String deadline) {
        this.auctionId = auctionId;
        this.auctionHouseUri = auctionHouseUri;
        this.taskUri = taskUri;
        this.taskType = taskType;
        this.deadline = deadline;
    }

    public AuctionJsonRepresentation(Auction auction) {
        this.auctionId = auction.getAuctionId().getValue();
        this.auctionHouseUri = auction.getAuctionHouseUri().getValue().toString();
        this.taskUri = auction.getTaskUri().getValue().toString();
        this.taskType = auction.getTaskType().getValue();
        this.deadline = auction.getDeadline().getValue();
    }

    public static String serialize(Auction auction) throws JsonProcessingException {
        AuctionJsonRepresentation representation = new AuctionJsonRepresentation(auction);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(representation);
    }

    public static Auction deserialize(String auctionString) throws JsonProcessingException {
        JsonNode auctionData = new ObjectMapper().readTree(auctionString);
        Auction.AuctionHouseUri auctionHouseUri =
            new Auction.AuctionHouseUri(URI.create(auctionData.get("auctionHouseUri").textValue()));
        Auction.AuctionedTaskUri taskUri =
            new Auction.AuctionedTaskUri(URI.create(auctionData.get("auctionHouseUri").textValue()));
        Auction.AuctionedTaskType taskType=
            new Auction.AuctionedTaskType(auctionData.get("taskType").textValue());
        Auction.AuctionDeadline deadline =
            new Auction.AuctionDeadline(auctionData.get("deadline").textValue());
        return new Auction(auctionHouseUri, taskUri, taskType, deadline);
    }
}
