package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.TaskJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.port.out.AuctionWonEventPort;
import ch.unisg.tapas.auctionhouse.domain.*;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
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
            URI taskWinnerUri = URI.create(event.getWinningBid().getBidderAuctionHouseUri().getValue() + "/taskwinner");

            log.info("Sending notification to task winner URI: "+taskWinnerUri.toString());
            // Retrieve the task object from the task list service
            try {
                log.info("Retrieving task object from task list service at {}", taskUri);
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

                log.info("Sending task to external auction house at {}", taskWinnerUri);
                // Send task object to winner organization
                request = HttpRequest.newBuilder()
                    .uri(taskWinnerUri)
                    .headers("Content-Type", TaskJsonRepresentation.MEDIA_TYPE)
                    .POST(HttpRequest.BodyPublishers.ofString(TaskJsonRepresentation.serialize(task)))
                    .build();

                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 202)
                    throw new RuntimeException("Sending won task to bidder " + taskWinnerUri.toString() +
                        " resulted in code " + response.statusCode() + " but 202 is expected");

                log.info("Successfully sent task to task winner");

            } catch (Exception e) {
                throw new RuntimeException("Could not send won task to auction winner", e);
            }

        }
    }
}
