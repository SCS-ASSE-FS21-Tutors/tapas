package ch.unisg.tapas.auctionhouse.adapter.out.messaging.mqtt;

import ch.unisg.tapas.auctionhouse.adapter.common.clients.TapasMqttClient;
import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionJsonRepresentation;
import ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt.AuctionEventsMqttDispatcher;
import ch.unisg.tapas.auctionhouse.application.port.out.AuctionStartedEventPort;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.AuctionStartedEvent;
import ch.unisg.tapas.common.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PublishAuctionStartedEventMqttAdapter implements AuctionStartedEventPort {

    private AuctionEventsMqttDispatcher dispatcher = new AuctionEventsMqttDispatcher();
    private String MQTT_BROKER = "tcp://broker.hivemq.com:1883";
    private TapasMqttClient client = TapasMqttClient.getInstance(MQTT_BROKER, dispatcher);

    @Override
    public void publishAuctionStartedEvent(AuctionStartedEvent event) {
        String auctionTopic = "ch/unisg/tapas/auctions";
        Auction auction = event.getAuction();
        try {
            // Create a JSON string from auction object and publish MQTT message
            String auctionJson = AuctionJsonRepresentation.serialize(auction);
            client.publishMessage(auctionTopic, auctionJson);
        }catch (Exception e) {
            //TODO: Handle Exception Correctly
            System.out.println(e);
        }
    }
}
