package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.TaskJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.port.out.AuctionWonEventPort;
import ch.unisg.tapas.auctionhouse.domain.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * This class is a template for sending auction won events via HTTP. This class was created here only
 * as a placeholder, it is up to you to decide how such events should be sent (e.g., via HTTP,
 * WebSub, etc.).
 */
@Component
@Primary
public class AuctionWonEventHttpAdapter implements AuctionWonEventPort {

    private HttpClient client;

    public AuctionWonEventHttpAdapter() {
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public void publishAuctionWonEvent(AuctionWonEvent event) {
        // Get the task URI from the auction that was won
        Optional<Auction> auction = AuctionRegistry.getInstance().getAuctionById(event.getWinningBid().getAuctionId());
        if (auction.isPresent()) {
            URI taskUri = auction.get().getTaskUri().getValue();
            // Retrieve the task object from the task list service
            try {
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(taskUri)
                    .headers("Content-Type", TaskJsonRepresentation.MEDIA_TYPE)
                    .GET()
                    .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 200)
                    throw new RuntimeException("Retrieving Task from URI " + taskUri.toString() +
                        " resulted in code " + response.statusCode() + " but 200 is expected");
                Task task = TaskJsonRepresentation.deserialize(response.body());

                // Set original task uri to source task uri
                task.setOriginalTaskUri(new Task.OriginalTaskUri(taskUri.toString()));

                // Send task object to winner organization
                request = HttpRequest.newBuilder()
                    .uri(URI.create(event.getWinningBid().getBidderAuctionHouseUri().getValue() + "taskwinner"))
                    .headers("Content-Type", TaskJsonRepresentation.MEDIA_TYPE)
                    .POST(HttpRequest.BodyPublishers.ofString(TaskJsonRepresentation.serialize(task)))
                    .build();

                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 202)
                    throw new RuntimeException("Sending won task to bidder " + event.getWinningBid().getBidderAuctionHouseUri().getValue().toString() +
                        " resulted in code " + response.statusCode() + " but 202 is expected");


            } catch (Exception e) {
                throw new RuntimeException("Could not send won task to auction winner", e);
            }

        }
    }
}
