package ch.unisg.tapas.auctionhouse.application.handler;

import ch.unisg.tapas.auctionhouse.application.port.in.BidPlacedEvent;
import ch.unisg.tapas.auctionhouse.application.port.in.BidPlacedEventHandler;
import ch.unisg.tapas.auctionhouse.domain.AuctionRegistry;
import org.springframework.stereotype.Component;

@Component
public class BidPlacedHandler implements BidPlacedEventHandler {
    @Override
    public boolean handleBidPlacedEvent(BidPlacedEvent event) {
        return AuctionRegistry.getInstance().placeBid(event.getBid());
    }
}
