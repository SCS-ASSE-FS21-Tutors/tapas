package ch.unisg.tapas;

import ch.unisg.tapas.auctionhouse.adapter.in.messaging.websub.WebSubMarketplaceBootstrapper;
import ch.unisg.tapas.auctionhouse.application.port.in.AuctionHouseDiscoveryUseCase;
import ch.unisg.tapas.auctionhouse.application.port.in.StoreKnownAuctionHouseCommand;
import ch.unisg.tapas.auctionhouse.application.port.in.StoreKnownAuctionHouseUseCase;
import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;
import ch.unisg.tapas.auctionhouse.domain.Task;
import ch.unisg.tapasbase.TapasMicroservice;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main TAPAS Auction House application.
 */
@Log4j2
@TapasMicroservice
@SpringBootApplication
public class TapasAuctionHouseApplication {


    @Autowired
    private StoreKnownAuctionHouseUseCase storeKnownAuctionHouseUseCase;
    @Autowired
    private AuctionHouseDiscoveryUseCase auctionHouseDiscoveryUseCase;
    @Autowired
    private WebSubMarketplaceBootstrapper webSubMarketplaceBootstrapper;

    @Value("${ch.unisg.tapas.group-name}")
    private String groupName;

    public static void main(String[] args) {
        new SpringApplication(TapasAuctionHouseApplication.class).run(args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            // Store hardcoded auction houses that we know
            // Later we can load them from a database or some kind of configuration file
            loadAuctionHousesToPropagate().stream().map(StoreKnownAuctionHouseCommand::new).forEach(storeKnownAuctionHouseUseCase::storeAuctionHouseToPropagate);

            // Crawl others
            log.info("Start crawling auction houses");
            var info = auctionHouseDiscoveryUseCase.discoverAuctionHouses(URI.create("http://localhost:8085"));
            log.info("Crawled auction houses: " + info.toString());
            log.info("Found a total of {} auction houses: {}",
                info.size(),
                info.stream().map(i -> i.getGroupName().getValue()).collect(Collectors.toList()));

            // Store known auction houses except ourselves
            info.stream()
                .map(StoreKnownAuctionHouseCommand::new)
                .forEach(storeKnownAuctionHouseUseCase::storeKnownAuctionHouse);

            // Bootstrap WebSub
            webSubMarketplaceBootstrapper.bootstrapMarketplaceWithWebSub();
        };
    }

    public Collection<AuctionHouseInformation> loadAuctionHousesToPropagate(){

        try{
            var now = new Date();
            var dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            var timestampString = dateFormat.format(now);

            return List.of(
                new AuctionHouseInformation(
                    new URI("https://tapas-auction-house.86-119-34-242.nip.io"),
                    new URI("http://tapas-auction-house.86-119-34-242.nip.io/websub-subscribe"),
                    List.of(new Task.TaskType("SMALLROBOT"), new Task.TaskType("COMPUTATION")),
                    new AuctionHouseInformation.AuctionHouseTimeStamp(timestampString),
                    new AuctionHouseInformation.GroupName(groupName)),
                new AuctionHouseInformation(
                    new URI("https://tapas-auction-house.86-119-35-213.nip.io"),
                    new URI("https://tapas-auction-house.86-119-35-213.nip.io"),
                    List.of(new Task.TaskType("COMPUTATION"), new Task.TaskType("SMALLROBOT")),
                    new AuctionHouseInformation.AuctionHouseTimeStamp("2021-12-13 09:14:23.675826972"),
                    new AuctionHouseInformation.GroupName("Group2")
                ),
                new AuctionHouseInformation(
                    new URI("https://tapas-auction-house.86-119-35-199.nip.io"),
                    new URI("https://tapas-auction-house.86-119-35-199.nip.io"),
                    List.of(new Task.TaskType("RANDOMTEXT"), new Task.TaskType("SMALLROBOT")),
                    new AuctionHouseInformation.AuctionHouseTimeStamp("2021-12-13 12:16:55"),
                    new AuctionHouseInformation.GroupName("tapas-group4")
                ));
        }
        catch (URISyntaxException exception){
            throw new RuntimeException(exception);
        }

    }
}
