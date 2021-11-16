package ch.unisg.tapas.auctionhouse.application.handler;

import ch.unisg.tapas.auctionhouse.application.port.in.BidReceivedEvent;
import ch.unisg.tapas.auctionhouse.application.port.in.BidReceivedEventHandler;
import ch.unisg.tapas.auctionhouse.domain.AuctionRegistry;
import org.springframework.stereotype.Component;

@Component
public class BidReceivedHandler implements BidReceivedEventHandler {
    @Override
    public boolean handleNewBidReceivedEvent(BidReceivedEvent bidReceivedEvent){
        var auction = AuctionRegistry.getInstance().getAuctionById(bidReceivedEvent.bid.getAuctionId());
        // TODO Handle if auction not there
        auction.get().addBid(bidReceivedEvent.bid);
        return true;
    }
}
