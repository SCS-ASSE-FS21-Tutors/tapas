package ch.unisg.tapasexecutorpool.pool.application.port.in;

import ch.unisg.tapascommon.common.SelfValidating;
import ch.unisg.tapasexecutorpool.pool.domain.Executor.ExecutorName;
import ch.unisg.tapasexecutorpool.pool.domain.Executor.ExecutorType;
import ch.unisg.tapasexecutorpool.pool.domain.Executor.ExecutorAddress;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Value
public class AddNewExecutorToPoolCommand extends SelfValidating<AddNewExecutorToPoolCommand> {
    @NotNull
    ExecutorName executorName;

    @NotNull
    ExecutorType executorType;

    @NotNull
    ExecutorAddress executorAddress;

    public AddNewExecutorToPoolCommand(ExecutorName executorName, ExecutorType executorType, ExecutorAddress executorAddress) {
        this.executorName = executorName;
        this.executorType = executorType;
        this.executorAddress = executorAddress;
        this.validateSelf();
    }
}
