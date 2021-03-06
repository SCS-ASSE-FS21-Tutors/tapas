package ch.unisg.tapas;

import ch.unisg.tapas.auctionhouse.adapter.common.clients.TapasMqttClient;
import ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt.AuctionEventsMqttDispatcher;
import ch.unisg.tapas.auctionhouse.adapter.common.clients.WebSubSubscriber;
import ch.unisg.tapas.common.AuctionHouseResourceDirectory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.URI;
import java.util.List;

/**
 * Main TAPAS Auction House application.
 */
@SpringBootApplication
public class TapasAuctionHouseApplication {
    private static final Logger LOGGER = LogManager.getLogger(TapasAuctionHouseApplication.class);

    public static String RESOURCE_DIRECTORY = "https://api.interactions.ics.unisg.ch/auction-houses/";
    public static String MQTT_BROKER = "tcp://broker.hivemq.com:1883";

    private static ConfigurableEnvironment ENVIRONMENT;

    public static void main(String[] args) {
		SpringApplication tapasAuctioneerApp = new SpringApplication(TapasAuctionHouseApplication.class);
        ENVIRONMENT = tapasAuctioneerApp.run(args).getEnvironment();

		// We will use these bootstrap methods in Week 6:
        // bootstrapMarketplaceWithWebSub();
        // bootstrapMarketplaceWithMqtt();
	}

    /**
     * Discovers auction houses and subscribes to WebSub notifications
     */
	private static void bootstrapMarketplaceWithWebSub() {
        List<String> auctionHouseEndpoints = discoverAuctionHouseEndpoints();
        LOGGER.info("Found auction house endpoints: " + auctionHouseEndpoints);

        WebSubSubscriber subscriber = new WebSubSubscriber();

        for (String endpoint : auctionHouseEndpoints) {
            subscriber.subscribeToAuctionHouseEndpoint(URI.create(endpoint));
        }
    }

    /**
     * Connects to an MQTT broker, presumably the one used by all TAPAS groups to communicate with
     * one another
     */
    private static void bootstrapMarketplaceWithMqtt() {
        try {
            String broker = ENVIRONMENT.getProperty("broker.mqtt");

            if (broker == null) {
                broker = MQTT_BROKER;
                LOGGER.info("No MQTT broker was set in application.propreties, going with default: "
                    + MQTT_BROKER);
            }

            AuctionEventsMqttDispatcher dispatcher = new AuctionEventsMqttDispatcher();
            TapasMqttClient client = TapasMqttClient.getInstance(broker, dispatcher);
            client.startReceivingMessages();
        } catch (MqttException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

	private static List<String> discoverAuctionHouseEndpoints() {
        AuctionHouseResourceDirectory rd = new AuctionHouseResourceDirectory(
            URI.create(RESOURCE_DIRECTORY)
        );

	    return rd.retrieveAuctionHouseEndpoints();
    }
}
