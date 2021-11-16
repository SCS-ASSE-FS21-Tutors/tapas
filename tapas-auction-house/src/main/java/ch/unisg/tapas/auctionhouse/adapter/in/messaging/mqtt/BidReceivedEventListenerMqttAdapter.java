package ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.application.handler.BidReceivedHandler;
import ch.unisg.tapas.auctionhouse.application.handler.ExecutorAddedHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.ExecutorAddedEvent;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.Bid;
import ch.unisg.tapas.auctionhouse.application.port.in.BidReceivedEvent;
import ch.unisg.tapas.auctionhouse.domain.ExecutorRegistry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.net.URI;

public class BidReceivedEventListenerMqttAdapter extends AuctionEventMqttListener {
    private static final Logger LOGGER = LogManager.getLogger(BidReceivedEventListenerMqttAdapter.class);

    @Override
    public boolean handleEvent(MqttMessage message){
        String payload = new String(message.getPayload());

        try {
            // Note: this message representation is provided only as an example. You should use a
            // representation that makes sense in the context of your application.
            JsonNode data = new ObjectMapper().readTree(payload);

            String auctionId = data.get("auctionId").asText();
            String bidderName = data.get("bidderName").asText();
            String bidderAuctionHouseUri = data.get("bidderAuctionHouseUri").asText();
            String bidderTaskListUri = data.get("bidderTaskListUri").asText();

            BidReceivedEvent bidReceivedEvent = new BidReceivedEvent( new Bid(
                new Auction.AuctionId(auctionId),
                new Bid.BidderName(bidderName),
                new Bid.BidderAuctionHouseUri(URI.create(bidderAuctionHouseUri)),
                new Bid.BidderTaskListUri(URI.create(bidderTaskListUri))
            ));

            BidReceivedHandler bidReceivedHandler = new BidReceivedHandler();
            bidReceivedHandler.handleNewBidReceivedEvent(bidReceivedEvent);
        } catch (JsonProcessingException | NullPointerException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }

        return true;
    }
}
