package ch.unisg.tapas.roster.adapter.out.web;

import ch.unisg.tapas.common.formats.TaskJsonRepresentation;
import ch.unisg.tapas.roster.application.port.out.AuctionHousePort;
import ch.unisg.tapas.roster.entities.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@Log4j2
public class AuctionHouseWebAdapter implements AuctionHousePort {

    private final String auctionHouseUrl;
    private final ObjectMapper om;
    private final HttpClient client;

    public AuctionHouseWebAdapter(@Value( "${ch.unisg.tapas.auction-house-url}" ) String auctionHouseUrl) {

        this.auctionHouseUrl = auctionHouseUrl;
        this.om = new ObjectMapper();
        this.client = HttpClient.newHttpClient();

    }

    @Override
    public void executeExternally(Task task) {
        String targetUrl = auctionHouseUrl + "/internal/create-auction-for-task/";
        log.info("Sending task {} to auction house at {}", task.getTaskId().getValue(), targetUrl);

        try{
            // Serialize the Task object
            var taskJson = TaskJsonRepresentation.serialize(task);

            // Send task to executor pool
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(targetUrl))
                    .headers("Content-Type", TaskJsonRepresentation.MEDIA_TYPE)
                    .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != HttpStatus.ACCEPTED.value())
                throw new RuntimeException("Auction house responded with statusCode " + response.statusCode() + " but "+HttpStatus.ACCEPTED.value()+" is expected");
        }
        catch (Exception ex){
            throw new RuntimeException("Failed to send task to auction house", ex);
        }

    }
}
