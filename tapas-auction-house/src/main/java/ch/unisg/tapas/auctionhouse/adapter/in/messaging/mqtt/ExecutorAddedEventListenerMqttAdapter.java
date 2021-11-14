package ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.application.handler.ExecutorAddedHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.ExecutorAddedEvent;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.ExecutorRegistry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.net.URI;

/**
 * Listener that handles events when an executor was added to this TAPAS application.
 *
 * This class is only provided as an example to help you bootstrap the project.
 */
public class ExecutorAddedEventListenerMqttAdapter extends AuctionEventMqttListener {
    private static final Logger LOGGER = LogManager.getLogger(ExecutorAddedEventListenerMqttAdapter.class);

    @Override
    public boolean handleEvent(MqttMessage message) {
        String payload = new String(message.getPayload());

        try {
            // Note: this message representation is provided only as an example. You should use a
            // representation that makes sense in the context of your application.
            JsonNode data = new ObjectMapper().readTree(payload);

            String executorUri = data.get("executorUri").asText();
            String executorTaskType = data.get("executorTaskType").asText();

            ExecutorAddedEvent executorAddedEvent = new ExecutorAddedEvent(
                new ExecutorRegistry.ExecutorUri(URI.create(executorUri)),
                new Auction.AuctionedTaskType(executorTaskType)
            );

            ExecutorAddedHandler newExecutorHandler = new ExecutorAddedHandler();
            newExecutorHandler.handleNewExecutorEvent(executorAddedEvent);
        } catch (JsonProcessingException | NullPointerException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }

        return true;
    }
}
