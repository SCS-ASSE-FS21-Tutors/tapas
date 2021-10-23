package ch.unisg.tapasroster.roster.adapter.out.web;

import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToExecutorCommand;
import ch.unisg.tapasroster.roster.application.port.out.TriggerAuctionForTaskEventPort;
import ch.unisg.tapasroster.roster.domain.Task;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class TriggerAuctionForTaskEventHTTPAdapter implements TriggerAuctionForTaskEventPort {
    @Value("${roster.auctionHouse.url: auctionHouse_url_not_found}")
    private String auctionHouseUrl;

    @Override
    public boolean triggerAuctionForTask(AssignTaskToExecutorCommand assignTaskToExecutorCommand) {
        try {
            // Use AuctionMediaType to create a new Auction for a given Task
            String payload = AuctionMediaType.serialize(assignTaskToExecutorCommand);
            try {
                URI startAuctionUri = URI.create(auctionHouseUrl + "/auction/");
                HttpClient httpClient = HttpClient.newHttpClient();

                // Build POST-request with auction
                HttpRequest startAuctionRequest = HttpRequest
                        .newBuilder()
                        .uri(startAuctionUri)
                        .POST(HttpRequest.BodyPublishers.ofString(payload))
                        .header("Content-Type", AuctionMediaType.AUCTION_MEDIA_CONTENT_TYPE)
                        .build();

                // Send request with new auction to auction house
                HttpResponse<String> response = httpClient.send(
                        startAuctionRequest,
                        HttpResponse.BodyHandlers.ofString()
                );
                // Check whether request was successful
                if (response.statusCode() / 100 != 2) {
                    System.err.printf("Calling %s with %s returns StatusCode: %d\n",
                            startAuctionRequest.uri(), startAuctionRequest.method(), response.statusCode());
                    return false;
                }
                return true;
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
