package ch.unisg.tapasexecutorpool.pool.application.port.in;

import ch.unisg.tapasexecutorpool.common.SelfValidating;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.Executor.ExecutorType;
import ch.unisg.tapasexecutorpool.pool.domain.Executor.ExecutorName;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class AddNewExecutorToExecutorPoolCommand extends SelfValidating<AddNewExecutorToExecutorPoolCommand> {
    @NotNull
    private final ExecutorName executorName;

    @NotNull
    private final ExecutorType executorType;

    public AddNewExecutorToExecutorPoolCommand(Executor.ExecutorName executorName, ExecutorType executorType) {
        this.executorName = executorName;
        this.executorType = executorType;
        this.validateSelf();
    }
}
