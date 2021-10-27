package ch.unisg.tapas.auctionhouse.application.port.in;

import ch.unisg.tapas.auctionhouse.domain.Bid;

public interface BidPlacedUseCase {
    boolean placeBid(Bid bid);
}
