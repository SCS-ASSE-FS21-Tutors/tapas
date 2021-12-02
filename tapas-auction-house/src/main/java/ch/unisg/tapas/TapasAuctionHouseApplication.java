package ch.unisg.tapas;

import ch.unisg.tapas.auctionhouse.adapter.common.clients.TapasMqttClient;
import ch.unisg.tapas.auctionhouse.adapter.in.messaging.mqtt.AuctionEventsMqttDispatcher;
import ch.unisg.tapas.auctionhouse.adapter.common.clients.WebSubSubscriber;
import ch.unisg.tapas.auctionhouse.application.port.in.StoreKnownAuctionHouseUseCase;
import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;
import ch.unisg.tapas.auctionhouse.domain.Task;
import ch.unisg.tapas.common.AuctionHouseResourceDirectory;
import ch.unisg.tapasbase.TapasMicroservice;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Main TAPAS Auction House application.
 */
@Log4j2
@TapasMicroservice
@SpringBootApplication
public class TapasAuctionHouseApplication {

    private static ConfigurableEnvironment ENVIRONMENT;
    @Autowired
    StoreKnownAuctionHouseUseCase storeKnownAuctionHouseUseCase;

    public static void main(String[] args) {
        SpringApplication tapasAuctioneerApp = new SpringApplication(TapasAuctionHouseApplication.class);
        ENVIRONMENT = tapasAuctioneerApp.run(args).getEnvironment();
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            storeKnownAuctionHouseUseCase.storeKnownAuctionHouse(
                new AuctionHouseInformation(
                    new URI("http://idontknow"),
                    new URI("http://idontknow"),
                    List.of(new Task.TaskType("foobar")),
                    new AuctionHouseInformation.AuctionHouseTimeStamp("Timestamp"),
                    new AuctionHouseInformation.GroupName("Group0")
                    )
            );
            log.info("Added known auction houses");
        };
    }
}
