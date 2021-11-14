package ch.unisg.tapas;

import ch.unisg.tapas.auctionhouse.adapter.common.clients.TapasMqttClient;
import ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt.AuctionEventsMqttDispatcher;
import ch.unisg.tapas.auctionhouse.adapter.common.clients.WebSubSubscriber;
import ch.unisg.tapas.common.AuctionHouseResourceDirectory;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.util.List;

/**
 * Main TAPAS Auction House application.
 */
@OpenAPIDefinition(info = @Info(title = "Auction-House-API", version = "1"))
@SpringBootApplication
public class TapasAuctionHouseApplication {

    public static void main(String[] args) {
		SpringApplication tapasAuctioneerApp = new SpringApplication(TapasAuctionHouseApplication.class);
        tapasAuctioneerApp.run(args);
	}
}
