package ch.unisg.tapas.auctionhouse.adapter.in.messaging.websub;

import ch.unisg.tapas.auctionhouse.application.handler.AuctionStartedHandler;
import org.springframework.web.bind.annotation.*;

/**
 * This class is a template for handling auction started events received via WebSub
 */
@RestController
public class AuctionStartedEventListenerWebSubAdapter {
    private final AuctionStartedHandler auctionStartedHandler;

    public AuctionStartedEventListenerWebSubAdapter(AuctionStartedHandler auctionStartedHandler) {
        this.auctionStartedHandler = auctionStartedHandler;
    }

    //TODO
}
