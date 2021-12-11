package ch.unisg.tapas.auctionhouse.application.port.in;

import ch.unisg.tapas.auctionhouse.domain.AuctionHouseInformation;
import ch.unisg.tapas.auctionhouse.domain.Task;
import ch.unisg.tapas.common.SelfValidating;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class StoreKnownAuctionHouseCommand extends SelfValidating<StoreKnownAuctionHouseCommand> {
    @NotNull
    private final AuctionHouseInformation auctionHouseInformation;

    public StoreKnownAuctionHouseCommand(AuctionHouseInformation auctionHouseInformation){
        this.auctionHouseInformation = auctionHouseInformation;
        this.validateSelf();
    }

}
