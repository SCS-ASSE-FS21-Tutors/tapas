package ch.unisg.tapas.auctionhouse.adapter.common.clients;

import ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt.AuctionEventsMqttDispatcher;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * MQTT client for your TAPAS application. This class is defined as a singleton, but it does not have
 * to be this way. This class is only provided as an example to help you bootstrap your project.
 * You are welcomed to change this class as you see fit.
 */
@Component
@Log4j2
public class TapasMqttClient {

    private MqttClient mqttClient;
    private final String mqttClientId;
    private final String brokerAddress;

    private final MessageReceivedCallback messageReceivedCallback;

    private final AuctionEventsMqttDispatcher dispatcher;

    private TapasMqttClient(@Value("${ch.unisg.tapas.mqtt-broker}") String brokerAddress, @Autowired AuctionEventsMqttDispatcher dispatcher) {
        this.mqttClientId = UUID.randomUUID().toString();
        this.brokerAddress = brokerAddress;
        this.messageReceivedCallback = new MessageReceivedCallback();
        this.dispatcher = dispatcher;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void startReceivingMessages() throws MqttException {
        mqttClient = new org.eclipse.paho.client.mqttv3.MqttClient(brokerAddress, mqttClientId, new MemoryPersistence());
        mqttClient.connect();
        mqttClient.setCallback(messageReceivedCallback);

        subscribeToAllTopics();
        log.info("Started MQTT receiver");
    }

    public void stopReceivingMessages() throws MqttException {
        mqttClient.disconnect();
    }

    private void subscribeToAllTopics() throws MqttException {
        for (String topic : dispatcher.getAllTopics()) {
            subscribeToTopic(topic);
        }
    }

    private void subscribeToTopic(String topic) throws MqttException {
        mqttClient.subscribe(topic);
    }

    public void publishMessage(String topic, String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes(StandardCharsets.UTF_8));
        mqttClient.publish(topic, message);
    }

    private class MessageReceivedCallback implements MqttCallback {

        @Override
        public void connectionLost(Throwable cause) {  }

        @Override
        public void messageArrived(String topic, MqttMessage message) {
            log.info("Received new MQTT message for topic " + topic + ": "
                + new String(message.getPayload()));

            if (topic != null && !topic.isEmpty()) {
                dispatcher.dispatchEvent(topic, message);
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {  }
    }
}
