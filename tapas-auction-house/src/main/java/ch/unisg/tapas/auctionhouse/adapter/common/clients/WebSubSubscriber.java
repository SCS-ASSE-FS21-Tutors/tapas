package ch.unisg.tapas.auctionhouse.adapter.common.clients;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Subscribes to the WebSub hubs of auction houses discovered at run time. This class is instantiated
 * from {@link ch.unisg.tapas.TapasAuctionHouseApplication} when boostraping the TAPAS marketplace
 * via WebSub.
 */
public class WebSubSubscriber {

    // TODO get this somehow from properties file. But on clue how to do this with static variables
    static String WEBSUB_HUB_ENDPOINT = "http://localhost:3000";
    static String AUCTION_HOUSE_ENDPOINT = "http://localhost:8086";

    Logger logger = Logger.getLogger(WebSubSubscriber.class.getName());

    public void subscribeToAuctionHouseEndpoint(URI endpoint) {
        // TODO decide with other groups about auction house endpoint uri to discover websub topics
        // and replace the hardcoded one with it
        String topic = discoverWebSubTopic("http://localhost:3100/websub");

        if (topic == null) {
            return;
        }

        subscribeToWebSub(topic);

        // TODO Subscribe to the auction house endpoint via WebSub:
        // 1. Send a request to the auction house in order to discover the WebSub hub to subscribe to.
        // The request URI should depend on the design of the Auction House HTTP API.
        // 2. Send a subscription request to the discovered WebSub hub to subscribe to events relevant
        // for this auction house.
        // 3. Handle the validation of intent from the WebSub hub (see WebSub protocol).
        //
        // Once the subscription is activated, the hub will send "fat pings" with content updates.
        // The content received from the hub will depend primarily on the design of the Auction House
        // HTTP API.
        //
        // For further details see:
        // - W3C WebSub Recommendation: https://www.w3.org/TR/websub/
        // - the implementation notes of the WebSub hub you are using to distribute events
    }

    private String discoverWebSubTopic(String endpoint) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .GET()
                .build();


        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == HttpStatus.OK.value()) {
                // TODO decide with other groups about response structure and replace the hardcoded
                // uri with response uri
                JSONObject jsonObject = new JSONObject(response.body());
                System.out.println(jsonObject);
                return jsonObject.getString("topic");
            } else {
                logger.log(Level.SEVERE, "Could not find a websub uri");
            }
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return null;
    }

    private void subscribeToWebSub(String topic) {
        HttpClient client = HttpClient.newHttpClient();

        String body = new JSONObject()
        .put("hub.callback", AUCTION_HOUSE_ENDPOINT + "/auction-started")
        .put("hub.mode", "subscribe")
        .put("hub.topic", topic)
        .put("hub.ws", false)
        .toString();


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(WEBSUB_HUB_ENDPOINT))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();


        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }
}
