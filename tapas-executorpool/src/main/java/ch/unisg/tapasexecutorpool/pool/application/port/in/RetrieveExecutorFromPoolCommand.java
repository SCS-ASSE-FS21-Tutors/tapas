package ch.unisg.tapasexecutorpool.pool.application.port.in;

import ch.unisg.tapasexecutorpool.pool.domain.Executor.ExecutorId;
import ch.unisg.tapasexecutorpool.common.SelfValidating;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class RetrieveExecutorFromPoolCommand extends SelfValidating<RetrieveExecutorFromPoolCommand> {
    @NotNull
    private final ExecutorId executorId;

    public RetrieveExecutorFromPoolCommand(ExecutorId executorId) {
        this.executorId = executorId;
        this.validateSelf();
    }
}
