package ch.unisg.tapas.auctionhouse.application.port.in;

public interface BidReceivedEventHandler {

    boolean handleBidReceivedEvent(BidReceivedEvent bidReceivedEvent);
}
