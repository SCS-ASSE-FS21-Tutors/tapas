package ch.unisg.tapas.auctionhouse.application.port.out;

import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.common.SelfValidating;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class CheckForExecutorQuery extends SelfValidating<CheckForExecutorQuery> {
    @NotNull
    private final Auction auction;

    public CheckForExecutorQuery(Auction auction){
        this.auction = auction;
        this.validateSelf();
    }

}
