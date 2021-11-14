package ch.unisg.tapas.auctionhouse.application.port.in;

import ch.unisg.tapas.auctionhouse.domain.ExecutorRegistry;
import ch.unisg.tapas.auctionhouse.domain.ExecutorRegistry.ExecutorUri;
import ch.unisg.tapas.common.SelfValidating;
import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * Event that notifies the auction house that an executor has been removed from this TAPAS application.
 */
@Value
public class ExecutorRemovedEvent extends SelfValidating<ExecutorRemovedEvent> {
    @NotNull
    private final ExecutorUri executorUri;

    /**
     * Constructs an executor removed event.
     *
     * @param executorUri
     */
    public ExecutorRemovedEvent(ExecutorUri executorUri) {
        this.executorUri = executorUri;
        this.validateSelf();
    }
}
