package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import ch.unisg.tapasexecutorpool.common.SelfValidating;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor.ExecutorUrl;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor.ExecutorType;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class AddExecutorToPoolCommand extends SelfValidating<AddExecutorToPoolCommand> {
    @NotNull
    private final ExecutorUrl executorUrl;

    @NotNull
    private final ExecutorType executorType;

    public AddExecutorToPoolCommand(ExecutorUrl executorUrl, ExecutorType executorType) {
        this.executorUrl = executorUrl;
        this.executorType = executorType;
        this.validateSelf();
    }
}