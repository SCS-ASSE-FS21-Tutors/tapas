package ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.handler.AuctionStartedHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.AuctionStartedEvent;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.common.ConfigProperties;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AuctionAddedEventListenerMqttAdapter extends AuctionEventMqttListener {

    @Autowired
    private ConfigProperties config;

    @Autowired
    private AuctionStartedHandler auctionStartedHandler;

    @Override
    public boolean handleEvent(MqttMessage message) {
        Auction auction;

        try {
            auction = AuctionJsonRepresentation.deserialize(new String(message.getPayload()));

            // Check if auction is not from us
            if (auction.getAuctionHouseUri().getValue().toString().equals(config.getAuctionHouseUri().toString())) {
                AuctionStartedEvent auctionStartedEvent = new AuctionStartedEvent(auction);
                auctionStartedHandler.handleAuctionStartedEvent(auctionStartedEvent);
            }

            return true;
        } catch (Exception e) {
            log.warn("Cannot handle new auction started event", e);
            return false;
        }
    }
}
