package ch.unisg.tapas.auctionhouse.adapter.common.formats;

import ch.unisg.tapas.auctionhouse.adapter.common.UniformUrlStringFormatter;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Used to expose a representation of the state of an auction through an interface. This class is
 * only meant as a starting point when defining a uniform HTTP API for the Auction House: feel free
 * to modify this class as you see fit!
 */
public class AuctionJsonRepresentation {
    public static final String MEDIA_TYPE = "application/json";

    @Getter
    @Setter
    private String auctionId;

    @Getter
    @Setter
    private String auctionHouseUri;

    @Getter
    @Setter
    private String taskUri;

    @Getter
    @Setter
    private String taskType;

    @Getter
    @Setter
    private Timestamp deadline;

    public AuctionJsonRepresentation() {
    }

    public AuctionJsonRepresentation(String auctionId, String auctionHouseUri, String taskUri,
                                     String taskType, Integer deadline) {


        this.auctionId = auctionId;
        this.auctionHouseUri = auctionHouseUri;
        this.taskUri = taskUri;
        this.taskType = taskType;
        this.deadline = new Timestamp(System.currentTimeMillis() + deadline);
    }

    public AuctionJsonRepresentation(Auction auction) {
        this.auctionId = auction.getAuctionId().getValue();
        this.auctionHouseUri = auction.getAuctionHouseUri().getValue().toString();
        this.taskUri = auction.getTaskUri().getValue().toString();
        this.taskType = auction.getTaskType().getValue();
        this.deadline = new Timestamp(auction.getDeadline().getValue() + System.currentTimeMillis());
    }

    public static String serialize(Auction auction) throws JsonProcessingException {
        AuctionJsonRepresentation representation = new AuctionJsonRepresentation(auction);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return mapper.writeValueAsString(representation);
    }

    public static Auction deserialize(String auctionJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(auctionJson);
        Auction.AuctionId auctionId = new Auction.AuctionId(jsonNode.get("auctionId").asText());
        Auction.AuctionHouseUri auctionHouseUri = new Auction.AuctionHouseUri(URI.create(
            UniformUrlStringFormatter.cleanURL(jsonNode.get("auctionHouseUri").asText())));
        Auction.AuctionedTaskUri taskUri = new Auction.AuctionedTaskUri(URI.create(jsonNode.get("taskUri").asText()));
        Auction.AuctionedTaskType taskType = new Auction.AuctionedTaskType(jsonNode.get("taskType").asText());
        Auction.AuctionDeadline deadline = new Auction.AuctionDeadline(jsonNode.get("deadline").asInt());

        Auction auction = new Auction(auctionId, auctionHouseUri, taskUri, taskType, deadline);
        return auction;
    }
}
