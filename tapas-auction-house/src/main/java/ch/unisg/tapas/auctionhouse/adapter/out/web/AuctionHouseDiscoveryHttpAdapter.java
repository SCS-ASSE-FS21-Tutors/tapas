package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionHouseDiscoveryRepresentation;
import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
@Log4j2
public class AuctionHouseDiscoveryHttpAdapter implements ch.unisg.tapas.auctionhouse.application.port.out.AuctionHouseDiscoveryPort {


    private HttpClient client;
    private ObjectMapper om;

    public AuctionHouseDiscoveryHttpAdapter() {

        this.client = HttpClient.newHttpClient();
        this.om = new ObjectMapper();

        // Other groups might not updated the case
        this.om.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    @Override
    public List<AuctionHouseInformation> loadDiscoveryInfo(URI auctionHouseUri) throws Exception {
        URI discoveryEndpointUri = URI.create(auctionHouseUri.toString() + "/discovery/");
        log.info("Sending discovery request to " + discoveryEndpointUri);

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
