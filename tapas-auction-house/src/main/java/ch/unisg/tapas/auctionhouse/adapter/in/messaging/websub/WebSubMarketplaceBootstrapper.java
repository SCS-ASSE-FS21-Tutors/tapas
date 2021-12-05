package ch.unisg.tapas.auctionhouse.adapter.in.messaging.websub;

import ch.unisg.tapas.auctionhouse.adapter.common.clients.WebSubSubscriber;
import ch.unisg.tapas.common.AuctionHouseResourceDirectory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Log4j2
@Component
public class WebSubMarketplaceBootstrapper {

   @Value("${ch.unisg.tapas.resource-directory}")
    private String resourceDirectory;

   @Autowired
   private WebSubSubscriber webSubSubscriber;

    /**
     * Discovers auction houses and subscribes to WebSub notifications
     */
    @EventListener(ContextRefreshedEvent.class)
    private void bootstrapMarketplaceWithWebSub() {
        List<String> auctionHouseEndpoints = discoverAuctionHouseEndpoints();
        log.info("Found auction house endpoints: " + auctionHouseEndpoints);


        for (String endpoint : auctionHouseEndpoints) {
            webSubSubscriber.subscribeToAuctionHouseEndpoint(URI.create(endpoint));
        }

        log.info("Started WebSub Adapter");
    }

    private List<String> discoverAuctionHouseEndpoints() {
        AuctionHouseResourceDirectory rd = new AuctionHouseResourceDirectory(
            URI.create(resourceDirectory)
        );

        return rd.retrieveAuctionHouseEndpoints();
    }
}
