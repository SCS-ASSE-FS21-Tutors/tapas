package ch.unisg.roster.roster.application.port.in;

import lombok.Value;

import javax.validation.constraints.NotNull;

import ch.unisg.common.validation.SelfValidating;
import ch.unisg.common.valueobject.ExecutorURI;
import ch.unisg.roster.roster.domain.valueobject.ExecutorType;

/**
 * Event that notifies the auction house that an executor has been added to this TAPAS application.
 */
@Value
public class ExecutorAddedEvent extends SelfValidating<ExecutorAddedEvent> {
    @NotNull
    private final ExecutorURI executorURI;

    @NotNull
    private final ExecutorType executorType;

    /**
     * Constructs an executor added event.
     *
     * @param executorURI the identifier of the executor that was added to this TAPAS application
     */
    public ExecutorAddedEvent(ExecutorURI executorURI, ExecutorType executorType) {
        this.executorURI = executorURI;
        this.executorType = executorType;

        this.validateSelf();
    }
}
