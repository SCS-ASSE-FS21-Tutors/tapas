package ch.unisg.tapas.auctionhouse.application.port.in;

import ch.unisg.tapas.auctionhouse.domain.Bid;
import ch.unisg.tapas.common.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Value
public class BidPlacedEvent extends SelfValidating<AuctionStartedEvent> {
    @NotNull
    Bid bid;

    public BidPlacedEvent(Bid bid) {
        this.bid = bid;
        this.validateSelf();
    }
}
