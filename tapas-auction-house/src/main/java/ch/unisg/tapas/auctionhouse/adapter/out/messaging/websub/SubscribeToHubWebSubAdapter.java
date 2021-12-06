package ch.unisg.tapas.auctionhouse.adapter.out.messaging.websub;

import ch.unisg.tapas.auctionhouse.application.port.out.SubscribeToHubPort;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Log4j2
public class SubscribeToHubWebSubAdapter implements SubscribeToHubPort {

    private final HttpClient client;

    private final String auctionHouseUri;



    public SubscribeToHubWebSubAdapter(@Value("${auction.house.uri}") String auctionHouseUri){
        this.client = HttpClient.newHttpClient();
        this.auctionHouseUri =auctionHouseUri;
    }

    public void subscribeToHub(URI hubUri, URI topicUri) {
        log.info("WebSub | Sending subscription request to hub: " + hubUri.toString());

        Map<String, String> parameters = new HashMap<>();
        parameters.put("hub.callback", auctionHouseUri + "/websub-auction-started");
        parameters.put("hub.mode", "subscribe");
        parameters.put("hub.topic", topicUri.toString());

        String form = parameters.keySet().stream()
            .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
            .collect(Collectors.joining("&"));


        HttpRequest request = HttpRequest.newBuilder()
            .uri(hubUri)
            .headers("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString(form))
            .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()==202){
                log.info("WebSub | Subscription request successful");
            } else {
                log.warn("WebSub | Subscription request for topic "+ topicUri.toString() + " resulted in response code " + response.statusCode());
            }
        }catch (Exception e){
            log.warn("WebSub | Subscription request for topic "+ topicUri.toString() + " failed");
            System.out.println(e);
        }

    }
}
