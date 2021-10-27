package ch.unisg.tapas.auctionhouse.application.port.in;

public interface BidPlacedEventHandler {
    boolean handleBidPlacedEvent(BidPlacedEvent event);
}
