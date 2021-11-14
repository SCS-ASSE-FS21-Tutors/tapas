package ch.unisg.tapas.auctionhouse.adapter.out.messaging.websub;

import ch.unisg.tapas.auctionhouse.adapter.common.clients.TapasMqttClient;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionJsonRepresentation;
import ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt.AuctionEventsMqttDispatcher;
import ch.unisg.tapas.auctionhouse.application.port.out.AuctionStartedEventPort;
import ch.unisg.tapas.auctionhouse.domain.AuctionStartedEvent;
import ch.unisg.tapas.common.ConfigProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PublishAuctionStartedEventMqttAdapter implements AuctionStartedEventPort {

    private static final Logger LOGGER = LogManager.getLogger(PublishAuctionStartedEventMqttAdapter.class);

    @Autowired
    private ConfigProperties config;

    @Override
    public void publishAuctionStartedEvent(AuctionStartedEvent event) {
        try{
            var mqttClient = TapasMqttClient.getInstance(config.getMqttBrokerUri().toString(), new AuctionEventsMqttDispatcher());
            mqttClient.publishMessage("ch/unisg/tapas/auctions", AuctionJsonRepresentation.serialize(event.getAuction()));
        }
        catch (MqttException | JsonProcessingException e){
            LOGGER.error(e.getMessage(), e);
        }
    }
}
