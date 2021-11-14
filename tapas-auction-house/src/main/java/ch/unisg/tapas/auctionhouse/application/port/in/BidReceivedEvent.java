package ch.unisg.tapas.auctionhouse.application.port.in;

import ch.unisg.tapas.auctionhouse.domain.Bid;
import ch.unisg.tapas.common.SelfValidating;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class BidReceivedEvent extends SelfValidating<BidReceivedEvent> {
    @NotNull
    private final Bid bid;

    public BidReceivedEvent(Bid bid) {
        this.bid = bid;
        this.validateSelf();
    }

}
