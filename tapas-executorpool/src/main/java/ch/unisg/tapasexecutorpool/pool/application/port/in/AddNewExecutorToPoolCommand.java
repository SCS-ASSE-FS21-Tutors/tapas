package ch.unisg.tapasexecutorpool.pool.application.port.in;


import ch.unisg.tapasexecutorpool.common.SelfValidating;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.Executor.ExecutorName;
import ch.unisg.tapasexecutorpool.pool.domain.Executor.ExecutorType;
import ch.unisg.tapasexecutorpool.pool.domain.Executor.ExecutorAddress;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class AddNewExecutorToPoolCommand extends SelfValidating<AddNewExecutorToPoolCommand> {
    @NotNull
    private final ExecutorName executorName;

    @NotNull
    private final ExecutorType executorType;

    @NotNull
    private final ExecutorAddress executorAddress;

    public AddNewExecutorToPoolCommand(ExecutorName executorName, ExecutorType executorType, ExecutorAddress executorAddress) {
        this.executorName = executorName;
        this.executorType = executorType;
        this.executorAddress = executorAddress;
        this.validateSelf();
    }
}
