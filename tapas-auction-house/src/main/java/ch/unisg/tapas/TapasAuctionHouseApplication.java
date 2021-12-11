package ch.unisg.tapas;

import ch.unisg.tapas.auctionhouse.application.port.in.StoreKnownAuctionHouseCommand;
import ch.unisg.tapas.auctionhouse.application.port.in.StoreKnownAuctionHouseUseCase;
import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;
import ch.unisg.tapas.auctionhouse.domain.Task;
import ch.unisg.tapasbase.TapasMicroservice;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
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

            var now = new Date();
            var dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            var timestampString = dateFormat.format(now);

            StoreKnownAuctionHouseCommand storeKnownAuctionHouseCommand = new StoreKnownAuctionHouseCommand(new AuctionHouseInformation(
                new URI("https://tapas-auction-house.86-119-34-242.nip.io/"),
                new URI("http://tapas-auction-house.86-119-34-242.nip.io/websub-subscribe"),
                List.of(new Task.TaskType("SMALLROBOT"), new Task.TaskType("COMPUTATION")),
                new AuctionHouseInformation.AuctionHouseTimeStamp(timestampString),
                new AuctionHouseInformation.GroupName("Group3")
            ));
            storeKnownAuctionHouseUseCase.storeKnownAuctionHouse(storeKnownAuctionHouseCommand);
            log.info("Added known auction houses");
        };
    }
}
