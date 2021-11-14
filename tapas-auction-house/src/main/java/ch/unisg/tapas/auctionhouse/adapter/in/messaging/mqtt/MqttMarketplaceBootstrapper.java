package ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.adapter.common.clients.TapasMqttClient;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MqttMarketplaceBootstrapper {

    @Value("${ch.unisg.tapas.mqtt-broker}")
    private String mqttBroker;

    /**
     * Connects to an MQTT broker, presumably the one used by all TAPAS groups to communicate with
     * one another
     */
    @EventListener(ContextRefreshedEvent.class)
    private void bootstrapMarketplaceWithMqtt() {
        try {
            AuctionEventsMqttDispatcher dispatcher = new AuctionEventsMqttDispatcher();
            TapasMqttClient client = TapasMqttClient.getInstance(mqttBroker, dispatcher);
            client.startReceivingMessages();
            log.info("Started MQTT receiver");
        } catch (MqttException e) {
            log.error(e.getMessage(), e);
        }
    }
}
