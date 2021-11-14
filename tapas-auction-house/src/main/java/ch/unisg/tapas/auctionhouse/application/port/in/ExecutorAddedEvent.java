package ch.unisg.tapas.auctionhouse.application.port.in;

import ch.unisg.tapas.auctionhouse.domain.Auction.AuctionedTaskType;
import ch.unisg.tapas.auctionhouse.domain.ExecutorRegistry;
import ch.unisg.tapas.auctionhouse.domain.ExecutorRegistry.ExecutorUri;
import ch.unisg.tapas.common.SelfValidating;
import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * Event that notifies the auction house that an executor has been added to this TAPAS application.
 */
@Value
public class ExecutorAddedEvent extends SelfValidating<ExecutorAddedEvent> {
    @NotNull
    private final ExecutorRegistry.ExecutorUri executorUri;

    @NotNull
    private final AuctionedTaskType taskType;

    /**
     * Constructs an executor added event.
     *
     * @param executorUri the identifier of the executor that was added to this TAPAS application
     */
    public ExecutorAddedEvent(ExecutorUri executorUri, AuctionedTaskType taskType) {
        this.executorUri = executorUri;
        this.taskType = taskType;

        this.validateSelf();
    }
}
