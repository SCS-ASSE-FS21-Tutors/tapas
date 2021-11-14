package ch.unisg.executorpool.adapter.common.clients;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class TapasMqttClient {
    private static final Logger LOGGER = LogManager.getLogger(TapasMqttClient.class);

    private static TapasMqttClient tapasClient = null;

    private MqttClient mqttClient;
    private final String mqttClientId;
    private final String brokerAddress;

    private TapasMqttClient(String brokerAddress) {
        this.mqttClientId = UUID.randomUUID().toString();
        this.brokerAddress = brokerAddress;
    }

    public static synchronized TapasMqttClient getInstance(String brokerAddress) {

        if (tapasClient == null) {
            tapasClient = new TapasMqttClient(brokerAddress);
        }

        return tapasClient;
    }

    public void publishMessage(String topic, String payload) throws MqttException {
        mqttClient = new org.eclipse.paho.client.mqttv3.MqttClient(brokerAddress, mqttClientId, new MemoryPersistence());
        mqttClient.connect();
        MqttMessage message = new MqttMessage(payload.getBytes(StandardCharsets.UTF_8));
        mqttClient.publish(topic, message);
        mqttClient.disconnect();
    }
}
