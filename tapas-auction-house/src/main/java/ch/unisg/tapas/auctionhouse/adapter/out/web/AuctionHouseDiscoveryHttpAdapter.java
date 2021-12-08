package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionHouseDiscoveryRepresentation;
import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class AuctionHouseDiscoveryHttpAdapter implements ch.unisg.tapas.auctionhouse.application.port.out.AuctionHouseDiscoveryPort {


    private HttpClient client = HttpClient.newHttpClient();
    private ObjectMapper om = new ObjectMapper();

    @Override
    public List<AuctionHouseInformation> loadDiscoveryInfo(URI auctionHouseUri) throws Exception {

        URI discoveryEndpointUri = URI.create(auctionHouseUri.toString() + "/discovery/");

        HttpRequest request = HttpRequest.newBuilder()
            .uri(discoveryEndpointUri)
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() >= 400)
            throw new RuntimeException("Discovery endpoint " + discoveryEndpointUri.toString() + " responded with status code " + response.statusCode());

        AuctionHouseDiscoveryRepresentation discoveryInfo = om.readValue(response.body(), AuctionHouseDiscoveryRepresentation.class);

        return discoveryInfo.toDomainObject();
    }

}
