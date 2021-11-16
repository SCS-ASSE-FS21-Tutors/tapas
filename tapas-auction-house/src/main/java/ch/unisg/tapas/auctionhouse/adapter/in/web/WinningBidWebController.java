package ch.unisg.tapas.auctionhouse.adapter.in.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionJsonRepresentation;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.BidJsonRepresentation;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.TaskJsonRepresentation;
import ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt.ExternalAuctionStartedEventListenerMqttAdapter;
import ch.unisg.tapas.auctionhouse.domain.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
public class WinningBidWebController {
    private static final Logger LOGGER = LogManager.getLogger(WinningBidWebController.class);

    @Value("${tasks.list.uri}")
    String taskListURI;

    @PostMapping(path = "/taskwinner", consumes = TaskJsonRepresentation.MEDIA_TYPE)
    public ResponseEntity<String> winningBid(@RequestBody TaskJsonRepresentation payload){
        try {
            var body = payload.serialize();
            LOGGER.info(body);
            var postURI = URI.create(taskListURI + "/tasks/");
            HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(postURI)
                .header("Content-Type", TaskJsonRepresentation.MEDIA_TYPE)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

            HttpClient client = HttpClient.newHttpClient();
            var postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());

            LOGGER.info(postResponse.statusCode());


            HttpHeaders responseHeaders = new HttpHeaders();
            return new ResponseEntity<>(responseHeaders, HttpStatus.NO_CONTENT);
        }
     catch (
         IOException | InterruptedException e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
    }
}
