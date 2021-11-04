package ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.handler.AuctionStartedHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.AuctionStartedEvent;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class AuctionStartedEventListenerMqttAdapter extends AuctionEventMqttListener {
    @Override
    public boolean handleEvent(MqttMessage message) {
        String payload = new String(message.getPayload());
        try {
            Auction auctionFromPayload = AuctionJsonRepresentation.deserialize(payload);
            AuctionStartedEvent auctionStartedEvent = new AuctionStartedEvent(
                auctionFromPayload
            );
            AuctionStartedHandler auctionStartedHandler = new AuctionStartedHandler();
            auctionStartedHandler.handleAuctionStartedEvent(auctionStartedEvent);
        } catch (JsonProcessingException e) {
            System.err.println("Could not handle MQTT message for AuctionStartedEvent:");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
