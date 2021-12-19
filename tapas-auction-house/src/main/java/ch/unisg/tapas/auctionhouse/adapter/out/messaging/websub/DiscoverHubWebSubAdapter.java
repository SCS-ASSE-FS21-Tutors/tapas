package ch.unisg.tapas.auctionhouse.adapter.out.messaging.websub;

import ch.unisg.tapas.auctionhouse.application.port.out.DiscoverHubPort;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Log4j2
public class DiscoverHubWebSubAdapter implements DiscoverHubPort {

    HttpClient client;

    public DiscoverHubWebSubAdapter() {
        client = HttpClient.newHttpClient();
    }

    public Map<String, Optional<String>> discoverHub(URI auctionHouseUri){

        log.info("WebSub | Sending discovery request to: " + auctionHouseUri.toString());

        Map<String, Optional<String>> uris = new HashMap<>();
        uris.put("hub", Optional.empty());
        uris.put("self", Optional.empty());

        HttpRequest request = HttpRequest.newBuilder()
            .uri(auctionHouseUri)
            .headers("Content-Type", "text/html")
            .GET()
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Get both the topic and hub URI from the auction house discovery headers
            Map<String, List<String>> map = response.headers().map();
            if (response.statusCode() == 200 && map.keySet().contains("link")) {
                for (String link : map.get("link")) {
                    if (link.contains("rel=\"hub\"")) {
                        String hubUri = link.split(";")[0];
                        hubUri = hubUri.substring(1, hubUri.length() - 1);
                        uris.put("hub", Optional.of(hubUri));
                        log.info("WebSub | Discovery \"hub\" header found: " + hubUri);
                    } else if (link.contains("rel=\"self\"")) {
                        String selfUri = link.split(";")[0];
                        selfUri = selfUri.substring(1, selfUri.length() - 1);
                        uris.put("self", Optional.of(selfUri));
                        log.info("WebSub | Discovery \"self\" header found: " + selfUri);
                    }
                }
            }
        } catch (Exception e){
            log.warn("WebSub | Discovery request unsuccessful");
            System.out.println(e);
        }
        return uris;
    }

}
