package ch.unisg.roster.roster.adapter.common.clients;

import ch.unisg.roster.roster.adapter.in.messaging.mqtt.ExecutorEventsMqttDispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * MQTT client for your TAPAS application. This class is defined as a singleton, but it does not have
 * to be this way. This class is only provided as an example to help you bootstrap your project.
 * You are welcomed to change this class as you see fit.
 */
public class TapasMqttClient {
    private static final Logger LOGGER = LogManager.getLogger(TapasMqttClient.class);

    private static TapasMqttClient tapasClient = null;

    private MqttClient mqttClient;
    private final String mqttClientId;
    private final String brokerAddress;

    private final MessageReceivedCallback messageReceivedCallback;

    private final ExecutorEventsMqttDispatcher dispatcher;

    private TapasMqttClient(String brokerAddress, ExecutorEventsMqttDispatcher dispatcher) {
        this.mqttClientId = UUID.randomUUID().toString();
        this.brokerAddress = brokerAddress;

        this.messageReceivedCallback = new MessageReceivedCallback();

        this.dispatcher = dispatcher;
    }

    public static synchronized TapasMqttClient getInstance(String brokerAddress,
            ExecutorEventsMqttDispatcher dispatcher) {

        if (tapasClient == null) {
            tapasClient = new TapasMqttClient(brokerAddress, dispatcher);
        }

        return tapasClient;
    }

    public void startReceivingMessages() throws MqttException {
        mqttClient = new org.eclipse.paho.client.mqttv3.MqttClient(brokerAddress, mqttClientId, new MemoryPersistence());
        mqttClient.connect();
        mqttClient.setCallback(messageReceivedCallback);

        subscribeToAllTopics();
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

    private void publishMessage(String topic, String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes(StandardCharsets.UTF_8));
        mqttClient.publish(topic, message);
    }

    private class MessageReceivedCallback implements MqttCallback {

        @Override
        public void connectionLost(Throwable cause) {  }

        @Override
        public void messageArrived(String topic, MqttMessage message) {
            LOGGER.info("Received new MQTT message for topic " + topic + ": "
                + new String(message.getPayload()));

            if (topic != null && !topic.isEmpty()) {
                dispatcher.dispatchEvent(topic, message);
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {  }
    }
}
