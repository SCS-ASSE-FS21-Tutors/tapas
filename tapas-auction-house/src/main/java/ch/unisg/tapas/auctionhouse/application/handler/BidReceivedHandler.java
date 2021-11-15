package ch.unisg.tapas.auctionhouse.application.handler;

import ch.unisg.tapas.auctionhouse.application.port.in.BidReceivedEvent;
import ch.unisg.tapas.auctionhouse.application.port.in.BidReceivedEventHandler;
import ch.unisg.tapas.auctionhouse.domain.AuctionRegistry;
import ch.unisg.tapas.auctionhouse.domain.Bid;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class BidReceivedHandler implements BidReceivedEventHandler {

    public boolean handleBidReceivedEvent(BidReceivedEvent bidReceivedEvent) {
        Bid bid = bidReceivedEvent.getBid();
        log.info("Storing received bid from \"" + bid.getBidderName().getValue() + "\" for auction " + bid.getAuctionId().getValue());
        return AuctionRegistry.getInstance().placeBid(bid);
    }
}
