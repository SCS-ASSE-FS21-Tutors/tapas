package ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.application.handler.ExecutorAddedHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.ExecutorAddedEvent;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.ExecutorRegistry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Listener that handles events when an executor was added to this TAPAS application.
 *
 * This class is only provided as an example to help you bootstrap the project.
 */
public class ExecutorAddedEventListenerMqttAdapter extends AuctionEventMqttListener {

    @Override
    public boolean handleEvent(MqttMessage message) {
        String payload = new String(message.getPayload());

        try {
            // Note: this messge representation is provided only as an example. You should use a
            // representation that makes sense in the context of your application.
            JsonNode data = new ObjectMapper().readTree(payload);

            String taskType = data.get("taskType").asText();
            String executorId = data.get("executorId").asText();

            ExecutorAddedEvent executorAddedEvent = new ExecutorAddedEvent(
                new ExecutorRegistry.ExecutorIdentifier(executorId),
                new Auction.AuctionedTaskType(taskType)
            );

            ExecutorAddedHandler newExecutorHandler = new ExecutorAddedHandler();
            newExecutorHandler.handleNewExecutorEvent(executorAddedEvent);
        } catch (JsonProcessingException | NullPointerException e) {
            // TODO: refactor logging
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
