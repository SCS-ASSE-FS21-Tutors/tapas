package ch.unisg.tapas.auctionhouse.adapter.in.messaging.websub;

import ch.unisg.tapas.auctionhouse.adapter.common.clients.WebSubSubscriber;
import ch.unisg.tapas.auctionhouse.application.port.in.RetrieveAuctionHouseInformationQuery;
import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;
import ch.unisg.tapas.common.AuctionHouseResourceDirectory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
public class WebSubMarketplaceBootstrapper {

    @Value("${ch.unisg.tapas.resource-directory}")
    private String resourceDirectory;

    @Autowired
    private WebSubSubscriber webSubSubscriber;

    @Autowired
    private RetrieveAuctionHouseInformationQuery retrieveAuctionHouseInformationQuery;

    @Value("${ch.unisg.tapas.group-name}")
    private String groupName;

    public void bootstrapMarketplaceWithWebSub() {
        List<String> auctionHouseEndpoints = discoverAuctionHouseEndpoints();
        log.info("Found auction house endpoints: " + auctionHouseEndpoints);


        for (String endpoint : auctionHouseEndpoints) {
            webSubSubscriber.subscribeToAuctionHouseEndpoint(URI.create(endpoint));
        }

        log.info("Started WebSub Adapter");
    }

    private List<String> discoverAuctionHouseEndpoints() {

        var knownAuctionHouses = retrieveAuctionHouseInformationQuery.loadKnownAuctionHouses();

        // Filter out our own
        return knownAuctionHouses.stream()
            .filter(a -> ! groupName.equals(a.getGroupName().getValue()))
            .map(a -> a.getWebsuburi().toString())
            .collect(Collectors.toList());
    }
}
