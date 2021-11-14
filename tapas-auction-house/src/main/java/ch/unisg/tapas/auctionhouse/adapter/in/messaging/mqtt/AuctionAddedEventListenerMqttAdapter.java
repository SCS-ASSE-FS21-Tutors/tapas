package ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.handler.AuctionStartedHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.AuctionStartedEvent;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.common.ConfigProperties;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class AuctionAddedEventListenerMqttAdapter extends AuctionEventMqttListener {

    @Autowired
    private ConfigProperties config;

    //TODO Check: Needed to hardcore the uri, not sure why I cannot retrieve with config or @value
    private String internalAuctionHouseUri = "https://tapas-auction-house.86-119-34-23.nip.io/";

    @Override
    public boolean handleEvent(MqttMessage message) {
        Auction auction;

        try {
            auction = AuctionJsonRepresentation.deserialize(new String(message.getPayload()));

            // Check if auction is not from us
            if (auction.getAuctionHouseUri().getValue().toString().equals(internalAuctionHouseUri)) {
                AuctionStartedEvent auctionStartedEvent = new AuctionStartedEvent(auction);
                AuctionStartedHandler auctionStartedHandler = new AuctionStartedHandler();
                auctionStartedHandler.handleAuctionStartedEvent(auctionStartedEvent);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
