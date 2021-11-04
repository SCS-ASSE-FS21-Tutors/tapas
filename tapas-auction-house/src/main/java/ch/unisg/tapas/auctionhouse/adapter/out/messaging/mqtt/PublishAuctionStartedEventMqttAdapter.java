package ch.unisg.tapas.auctionhouse.adapter.out.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.adapter.common.clients.TapasMqttClient;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionJsonRepresentation;
import ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt.AuctionEventsMqttDispatcher;
import ch.unisg.tapas.auctionhouse.application.port.out.AuctionStartedEventPort;
import ch.unisg.tapas.auctionhouse.domain.AuctionStartedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PublishAuctionStartedEventMqttAdapter implements AuctionStartedEventPort {
    @Value("${mqtt.broker.address: MQTT address not found in properties}")
    private String mqttBrokerAddress;

    @Value("${mqtt.client.topics.auctions: MQTT auction topic not found in properties}")
    private String auctionsTopic;

    @Override
    public void publishAuctionStartedEvent(AuctionStartedEvent event) {
        TapasMqttClient mqttClient = TapasMqttClient.getInstance(
            this.mqttBrokerAddress,
            new AuctionEventsMqttDispatcher()
        );
        try {
            String payload = AuctionJsonRepresentation.serialize(event.getAuction());
            try {
                mqttClient.publishMessage(
                    this.auctionsTopic,
                    payload
                );
            } catch (MqttException e) {
                System.err.printf(
                    "Could not send MQTT message to broker(%s): %s\n",
                    this.mqttBrokerAddress,
                    e.getMessage()
                );
                e.printStackTrace();
            }
        } catch (JsonProcessingException e) {
            System.err.printf("Could not serialize auction to JSON object: %s\n", e.getMessage());
            e.printStackTrace();
        }
    }
}
