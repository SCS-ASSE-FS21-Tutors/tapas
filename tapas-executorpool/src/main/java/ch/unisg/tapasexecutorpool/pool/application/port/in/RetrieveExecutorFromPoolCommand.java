package ch.unisg.tapasexecutorpool.pool.application.port.in;

import ch.unisg.tapasexecutorpool.pool.domain.Executor.ExecutorId;
import ch.unisg.tapasexecutorpool.common.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Value
public class RetrieveExecutorFromPoolCommand extends SelfValidating<RetrieveExecutorFromPoolCommand> {
    @NotNull
    ExecutorId executorId;

    public RetrieveExecutorFromPoolCommand(ExecutorId executorId) {
        this.executorId = executorId;
        this.validateSelf();
    }
}
