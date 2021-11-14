package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.BidJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.port.out.PlaceBidForAuctionCommand;
import ch.unisg.tapas.auctionhouse.application.port.out.PlaceBidForAuctionCommandPort;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.Bid;
import ch.unisg.tapas.auctionhouse.domain.Task;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This class is a tempalte for implementing a place bid for auction command via HTTP.
 */
@Component
@Primary
public class PlaceBidForAuctionCommandHttpAdapter implements PlaceBidForAuctionCommandPort {

    private HttpClient client;
    private ObjectMapper om;

    public PlaceBidForAuctionCommandHttpAdapter() {
        this.om = new ObjectMapper();
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public void placeBid(PlaceBidForAuctionCommand command) {

        Bid bid = command.getBid();
        Auction auction = command.getAuction();

        try {
            String bidJson = BidJsonRepresentation.serialize(bid);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(auction.getAuctionHouseUri().getValue() + "/bid/"))
                .headers("Content-Type", BidJsonRepresentation.MEDIA_TYPE)
                .POST(HttpRequest.BodyPublishers.ofString(bidJson))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 204)
                throw new RuntimeException("Auction House with the URI " + auction.getAuctionHouseUri().getValue() +
                    " responded with code " + response.statusCode() + " but 204 is expected");

        } catch (Exception e) {

            throw new RuntimeException("Could not place bid", e);
        }
    }
}
