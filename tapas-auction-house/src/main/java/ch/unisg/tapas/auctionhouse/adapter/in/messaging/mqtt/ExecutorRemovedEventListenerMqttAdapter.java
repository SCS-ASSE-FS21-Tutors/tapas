package ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.application.handler.ExecutorRemovedHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.ExecutorRemovedEvent;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.ExecutorRegistry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Listener that handles events when an executor was removed to this TAPAS application.
 *
 * This class is only provided as an example to help you bootstrap the project.
 */
public class ExecutorRemovedEventListenerMqttAdapter extends AuctionEventMqttListener {
    private static final Logger LOGGER = LogManager.getLogger(ExecutorRemovedEventListenerMqttAdapter.class);

    @Override
    public boolean handleEvent(MqttMessage message) {
        String payload = new String(message.getPayload());

        try {
            // Note: this messge representation is provided only as an example. You should use a
            // representation that makes sense in the context of your application.
            JsonNode data = new ObjectMapper().readTree(payload);

            String executorId = data.get("executorId").asText();

            ExecutorRemovedEvent executorRemovedEvent = new ExecutorRemovedEvent(
                new ExecutorRegistry.ExecutorIdentifier(executorId)
            );

            ExecutorRemovedHandler newExecutorHandler = new ExecutorRemovedHandler();
            newExecutorHandler.handleNewExecutorEvent(executorRemovedEvent);
        } catch (JsonProcessingException | NullPointerException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }

        return true;
    }
}
