package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import ch.unisg.tapasexecutorpool.common.SelfValidating;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor.ExecutorId;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class RetrieveExecutorByIdQuery extends SelfValidating<RetrieveExecutorByIdQuery> {
    @NotNull
    private final ExecutorId executorId;

    public RetrieveExecutorByIdQuery(ExecutorId executorId) {
        this.executorId = executorId;
        this.validateSelf();
    }
}