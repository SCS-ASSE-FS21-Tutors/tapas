package ch.unisg.tapas;

import ch.unisg.tapas.auctionhouse.adapter.common.clients.ResourceDirectoryRegisterer;
import ch.unisg.tapas.auctionhouse.adapter.common.clients.TapasMqttClient;
import ch.unisg.tapas.auctionhouse.adapter.common.clients.WebSubConfig;
import ch.unisg.tapas.auctionhouse.adapter.common.clients.WebSubSubscriber;
import ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt.AuctionEventsMqttDispatcher;
import ch.unisg.tapas.common.AuctionHouseResourceDirectory;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@Component
public class BootstrapMarketplaceAfterStartup {

    private static final Logger LOGGER = LogManager.getLogger(BootstrapMarketplaceAfterStartup.class);

    public static final String RESOURCE_DIRECTORY = "https://api.interactions.ics.unisg.ch/auction-houses/";
    public static final String MQTT_BROKER = "tcp://broker.hivemq.com:1883";

    private final WebSubConfig webSubConfig;
    private final ResourceDirectoryRegisterer resourceDirectoryRegisterer;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        resourceDirectoryRegisterer.unregister();
        resourceDirectoryRegisterer.register();

        if (webSubConfig.isProductionEnvironment()) {
            bootstrapMarketplaceWithWebSub();
        } else {
            LOGGER.info("Subscribing to development WebSubHub");
            var subscriber = new WebSubSubscriber(webSubConfig);
            subscriber.subscribeToDevelopmentWebSubHub();
        }

        bootstrapMarketplaceWithMqtt();
    }

    /**
     * Discovers auction houses and subscribes to WebSub notifications
     */
    private void bootstrapMarketplaceWithWebSub() {
        List<String> auctionHouseEndpoints = discoverAuctionHouseEndpoints();
        LOGGER.info("Found auction house endpoints: " + auctionHouseEndpoints);
        var subscriber = new WebSubSubscriber(webSubConfig);
        for (String endpoint : auctionHouseEndpoints) {
            subscriber.subscribeToAuctionHouseEndpoint(URI.create(endpoint));
        }
    }

    /**
     * Connects to an MQTT broker, presumably the one used by all TAPAS groups to communicate with
     * one another
     */
    private void bootstrapMarketplaceWithMqtt() {
        try {
            var dispatcher = new AuctionEventsMqttDispatcher();
            var client = TapasMqttClient.getInstance(MQTT_BROKER, dispatcher);
            client.startReceivingMessages();
        } catch (MqttException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private List<String> discoverAuctionHouseEndpoints() {
        var directory = new AuctionHouseResourceDirectory(
            URI.create(RESOURCE_DIRECTORY)
        );
        return directory.retrieveAuctionHouseEndpoints();
    }
}
