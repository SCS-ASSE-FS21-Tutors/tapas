package ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.handler.AuctionStartedHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.AuctionStartedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class AuctionStartedEventMqttListener extends AuctionEventMqttListener {

    private static final Logger LOGGER = LogManager.getLogger(AuctionStartedEventMqttListener.class);

    @Override
    public boolean handleEvent(MqttMessage message) {
        var payloadAuctionEvent = new String(message.getPayload());
        try {
            LOGGER.debug("Received new open auction from AuctionStartedEvent via MQTT");
            var auctionRepresentation = AuctionJsonRepresentation.fromJsonString(payloadAuctionEvent);
            var auctionStartedEvent = new AuctionStartedEvent(auctionRepresentation);
            var auctionStartedHandler = new AuctionStartedHandler();
            auctionStartedHandler.handleAuctionStartedEvent(auctionStartedEvent);
        } catch (JsonProcessingException e) {
            LOGGER.warn("MQTT message error for AuctionStartedEvent");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
