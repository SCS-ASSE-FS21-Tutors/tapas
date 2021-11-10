package ch.unisg.tapas.auctionhouse.adapter.out.messaging.websub;

import ch.unisg.tapas.auctionhouse.application.port.out.AuctionStartedEventPort;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.AuctionStartedEvent;
import ch.unisg.tapas.common.ConfigProperties;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class is a template for publishing auction started events via WebSub.
 */
@Component
@Primary
public class PublishAuctionStartedEventWebSubAdapter implements AuctionStartedEventPort {
    // You can use this object to retrieve properties from application.properties, e.g. the
    // WebSub hub publish endpoint, etc.
    @Autowired
    private ConfigProperties config;

    @Value("${auctionhouse.uri}")
    private String auctionHouseUri;

    @Value("${websub.hub.uri}")
    private String webSubHubUri;

    Logger logger = Logger.getLogger(PublishAuctionStartedEventWebSubAdapter.class.getName());

    @Override
    public void publishAuctionStartedEvent(AuctionStartedEvent event) {
        HttpClient client = HttpClient.newHttpClient();

        String body = new JSONObject()
        .put("hub.url", auctionHouseUri + "/auctions")
        .put("hub.mode", "publish")
        .toString();


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(webSubHubUri))
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
