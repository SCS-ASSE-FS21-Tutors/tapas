package ch.unisg.tapas;

import ch.unisg.tapas.auctionhouse.adapter.common.clients.TapasMqttClient;
import ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt.AuctionEventsMqttDispatcher;
import ch.unisg.tapas.auctionhouse.adapter.common.clients.WebSubSubscriber;
import ch.unisg.tapas.common.AuctionHouseResourceDirectory;
import ch.unisg.tapasbase.TapasMicroservice;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
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
@TapasMicroservice
@SpringBootApplication
public class TapasAuctionHouseApplication {

    private static ConfigurableEnvironment ENVIRONMENT;

    public static void main(String[] args) {
        SpringApplication tapasAuctioneerApp = new SpringApplication(TapasAuctionHouseApplication.class);
        ENVIRONMENT = tapasAuctioneerApp.run(args).getEnvironment();

    }
}
