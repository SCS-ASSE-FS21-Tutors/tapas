package ch.unisg.tapas.auctionhouse.adapter.out.messaging.websub;

import ch.unisg.tapas.auctionhouse.application.port.out.AuctionStartedEventPort;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.AuctionStartedEvent;
import ch.unisg.tapas.common.ConfigProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is a template for publishing auction started events via WebSub.
 */
@Component
@Log4j2
public class PublishAuctionStartedEventWebSubAdapter implements AuctionStartedEventPort {
    // You can use this object to retrieve properties from application.properties, e.g. the
    // WebSub hub publish endpoint, etc.
    @Autowired
    private ConfigProperties config;
    private HttpClient client;

    public PublishAuctionStartedEventWebSubAdapter() {
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public void publishAuctionStartedEvent(AuctionStartedEvent event) {
        Auction auction = event.getAuction();
        log.info("WebSub | Publishing auction event for auction ID: " + auction.getAuctionId());

        Map<String, String> parameters = new HashMap<>();
        parameters.put("hub.mode", "publish");
        parameters.put("hub.url", config.getAuctionHouseUri().toString() + "/websub-subscribe");

        String form = parameters.keySet().stream()
            .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
            .collect(Collectors.joining("&"));

        HttpRequest request = HttpRequest.newBuilder()
            .uri(config.getWebSubPublishEndpoint())
            .headers("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString(form))
            .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("WebSub | Publishing auction event resulted in response code "+ response.statusCode()+
                " and body " + response.body());
        }catch (Exception e){
            log.warn("WebSub | Publishing auction event for "+ auction.getAuctionId() + " failed");
            System.out.println(e);
        }

    }
}
