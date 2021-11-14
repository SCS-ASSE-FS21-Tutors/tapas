package ch.unisg.tapas.auctionhouse.adapter.common.formats;

import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapascommon.ServiceHostAddresses;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.util.UriBuilder;

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
    private Integer deadline;

    public AuctionJsonRepresentation() {  }

    public AuctionJsonRepresentation(
        String auctionId,
        String auctionHouseUri,
        String taskUri,
        String taskType,
        Integer deadline)
    {
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

    public static Auction deserialize(AuctionJsonRepresentation representation) {
        return new Auction(
            new Auction.AuctionId(representation.auctionId),
            new Auction.AuctionHouseUri(URI.create(representation.auctionHouseUri)),
            new Auction.AuctionedTaskUri(URI.create(representation.taskUri)),
            new Auction.AuctionedTaskType(representation.taskType),
            new Auction.AuctionDeadline(representation.deadline)
        );
    }

    public Auction deserialize() {
        return AuctionJsonRepresentation.deserialize(this);
    }

    public static AuctionJsonRepresentation fromJsonString(String auctionString) throws JsonProcessingException {
        var dataAuction = new ObjectMapper().readTree(auctionString);
        var auctionId = dataAuction.get("auctionId").textValue();
        var auctionHouseUri = dataAuction.get("auctionHouseUri").textValue();
        var taskUri = dataAuction.get("auctionHouseUri").textValue();
        var taskType= dataAuction.get("taskType").textValue();
        var deadline = dataAuction.get("deadline").intValue();
        return new AuctionJsonRepresentation(auctionId, auctionHouseUri, taskUri, taskType, deadline);
    }
}
