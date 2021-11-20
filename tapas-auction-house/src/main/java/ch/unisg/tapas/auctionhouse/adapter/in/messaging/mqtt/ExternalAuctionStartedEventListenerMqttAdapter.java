package ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.BidJsonRepresentation;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.TaskJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.handler.BidReceivedHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.BidReceivedEvent;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.Bid;
import ch.unisg.tapas.auctionhouse.domain.ExecutorRegistry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;

public class ExternalAuctionStartedEventListenerMqttAdapter extends AuctionEventMqttListener{
    private static final Logger LOGGER = LogManager.getLogger(ExternalAuctionStartedEventListenerMqttAdapter.class);

    String auctionHouseURI = "https://tapas-auction-house.86-119-35-40.nip.io/";

    String taskListURI = "https://tapas-tasks.86-119-35-40.nip.io";

    @Override
    public boolean handleEvent(MqttMessage message){
        String payload = new String(message.getPayload());

        try {
            // Note: this message representation is provided only as an example. You should use a
            // representation that makes sense in the context of your application.
            JsonNode data = new ObjectMapper().readTree(payload);

            // TODO Sanitize URIs
            String auctionId = data.get("auctionId").asText();
            String auctionHouseUri = data.get("auctionHouseUri").asText();
            String taskUri = data.get("taskUri").asText();
            String taskType = data.get("taskType").asText();
            String deadline = data.get("deadline").asText();

            var capable = ExecutorRegistry.getInstance().containsTaskType(new Auction.AuctionedTaskType(taskType));
            // TODO check deadline
            if(capable){
                var bid = new Bid(
                    new Auction.AuctionId(auctionId),
                    new Bid.BidderName("Group-1"),
                    new Bid.BidderAuctionHouseUri(URI.create(auctionHouseURI)),
                    new Bid.BidderTaskListUri(URI.create(taskListURI))
                );

                String body = BidJsonRepresentation.serialize(bid);
                LOGGER.info(body);
                var postURI = URI.create(auctionHouseUri + "/bid");
                HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(postURI)
                    .header("Content-Type", BidJsonRepresentation.MEDIA_TYPE)
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

                HttpClient client = HttpClient.newHttpClient();
                var postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());

                LOGGER.info(postResponse.statusCode());
            }
        } catch (JsonProcessingException | NullPointerException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        } catch (IOException e) {

            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (InterruptedException e) {

            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }

        return true;
    }
}
