package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.TaskJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.handler.AuctionStartedHandler;
import ch.unisg.tapas.auctionhouse.application.port.out.AuctionWonEventPort;
import ch.unisg.tapas.auctionhouse.domain.AuctionRegistry;
import ch.unisg.tapas.auctionhouse.domain.AuctionWonEvent;
import ch.unisg.tapas.auctionhouse.domain.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This class is a template for sending auction won events via HTTP. This class was created here only
 * as a placeholder, it is up to you to decide how such events should be sent (e.g., via HTTP,
 * WebSub, etc.).
 */
@Component
@Primary
public class AuctionWonEventHttpAdapter implements AuctionWonEventPort {
    private static final Logger LOGGER = LogManager.getLogger(AuctionWonEventHttpAdapter.class);

    @Value("${tasks.list.uri}")
    String server;

    @Override
    public void publishAuctionWonEvent(AuctionWonEvent event) {

        try{
            var auction = AuctionRegistry.getInstance().getAuctionById(event.getWinningBid().getAuctionId());

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(auction.get().getTaskUri().getValue())
                .GET()
                .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.info(response.body());
            JSONObject responseBody = new JSONObject(response.body());

            var task = new Task(
                new Task.TaskName(responseBody.getString("taskName")),
                new Task.TaskType(responseBody.getString("taskType")),
                new Task.OriginalTaskUri(auction.get().getTaskUri().getValue().toString()),
                new Task.TaskStatus(ch.unisg.tapas.auctionhouse.domain.Task.Status.ASSIGNED),
                new Task.TaskId(responseBody.getString("taskId")),
                new Task.InputData(responseBody.getString("inputData")),
                new Task.ServiceProvider("TODO")
            );

            var bidderAuctionHouseUri = event.getWinningBid().getBidderAuctionHouseUri().getValue().toString();
            String body = TaskJsonRepresentation.serialize(task);
            LOGGER.info(body);
            var postURI = URI.create(bidderAuctionHouseUri + "/taskwinner");
            LOGGER.info(postURI);
            HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(postURI)
                .header("Content-Type", TaskJsonRepresentation.MEDIA_TYPE)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

            var postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());

            LOGGER.info(postResponse.statusCode());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }
    }
}
