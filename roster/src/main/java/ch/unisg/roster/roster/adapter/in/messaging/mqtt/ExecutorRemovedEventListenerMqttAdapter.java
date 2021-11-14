package ch.unisg.roster.roster.adapter.in.messaging.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import ch.unisg.common.valueobject.ExecutorURI;
import ch.unisg.roster.roster.application.handler.ExecutorRemovedHandler;
import ch.unisg.roster.roster.application.port.in.ExecutorRemovedEvent;

public class ExecutorRemovedEventListenerMqttAdapter extends ExecutorEventMqttListener {
    private static final Logger LOGGER = LogManager.getLogger(ExecutorRemovedEventListenerMqttAdapter.class);

    @Override
    public boolean handleEvent(MqttMessage message) {
        String payload = new String(message.getPayload());

        try {
            // Note: this messge representation is provided only as an example. You should use a
            // representation that makes sense in the context of your application.
            JsonNode data = new ObjectMapper().readTree(payload);

            String executorId = data.get("executorURI").asText();

            ExecutorRemovedEvent executorRemovedEvent = new ExecutorRemovedEvent(
                new ExecutorURI(executorId));

            ExecutorRemovedHandler executorRemovedHandler = new ExecutorRemovedHandler();
            executorRemovedHandler.handleExecutorRemovedEvent(executorRemovedEvent);

        } catch (JsonProcessingException | NullPointerException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }

        return true;
    }
}
