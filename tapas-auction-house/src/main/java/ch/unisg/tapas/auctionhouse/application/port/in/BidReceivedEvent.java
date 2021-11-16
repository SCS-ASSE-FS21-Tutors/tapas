package ch.unisg.tapas.auctionhouse.application.port.in;

import ch.unisg.tapas.auctionhouse.domain.Bid;
import ch.unisg.tapas.common.SelfValidating;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class BidReceivedEvent extends SelfValidating<BidReceivedEvent> {
    @NotNull
    public Bid bid;

    public BidReceivedEvent(Bid bid){
        this.bid = bid;
        validateSelf();
    }
}
