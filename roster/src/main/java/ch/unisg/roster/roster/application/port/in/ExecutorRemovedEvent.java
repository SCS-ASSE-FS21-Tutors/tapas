package ch.unisg.roster.roster.application.port.in;

import lombok.Value;

import javax.validation.constraints.NotNull;

import ch.unisg.common.validation.SelfValidating;
import ch.unisg.common.valueobject.ExecutorURI;

/**
 * Event that notifies the auction house that an executor has been removed from this TAPAS application.
 */
@Value
public class ExecutorRemovedEvent extends SelfValidating<ExecutorRemovedEvent> {
    @NotNull
    private final ExecutorURI executorURI;

    /**
     * Constructs an executor removed event.
     *
     * @param executorURI the identifier of the executor that was removed from this TAPAS application
     */
    public ExecutorRemovedEvent(ExecutorURI executorURI) {
        this.executorURI = executorURI;
        this.validateSelf();
    }
}
