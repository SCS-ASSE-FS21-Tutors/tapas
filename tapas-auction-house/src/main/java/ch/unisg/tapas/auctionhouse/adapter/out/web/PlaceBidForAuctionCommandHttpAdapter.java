package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.BidJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.port.out.PlaceBidForAuctionCommand;
import ch.unisg.tapas.auctionhouse.application.port.out.PlaceBidForAuctionCommandPort;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Sends the place bid for an auction command via HTTP.
 */
@Component
@Primary
public class PlaceBidForAuctionCommandHttpAdapter implements PlaceBidForAuctionCommandPort {

    private static final String PATH = "/bid/{AuctionId}";

    @Override
    public void placeBid(PlaceBidForAuctionCommand command) {
        try {
            var auction = command.getAuction();
            var path = PATH.replace("{AuctionId}", auction.getAuctionId().getValue());
            var uri = auction.getAuctionHouseUri().getValue() + path;
            var json = BidJsonRepresentation.serialize(command.getBid());
            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .setHeader(HttpHeaders.CONTENT_TYPE, BidJsonRepresentation.MEDIA_TYPE)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
