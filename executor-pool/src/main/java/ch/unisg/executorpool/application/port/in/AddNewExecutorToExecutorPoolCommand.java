package ch.unisg.executorpool.application.port.in;

import ch.unisg.common.SelfValidating;
import ch.unisg.executorpool.domain.ExecutorPool;
import ch.unisg.executorpool.domain.ExecutorClass.ExecutorUri;
import ch.unisg.executorpool.domain.ExecutorClass.ExecutorTaskType;
import lombok.Value;
import javax.validation.constraints.NotNull;

@Value
public class AddNewExecutorToExecutorPoolCommand extends SelfValidating<AddNewExecutorToExecutorPoolCommand> {
    @NotNull
    private final ExecutorUri executorUri;

    @NotNull
    private final ExecutorTaskType executorTaskType;

    public AddNewExecutorToExecutorPoolCommand(ExecutorUri executorUri, ExecutorTaskType executorTaskType){
        this.executorUri = executorUri;
        this.executorTaskType = executorTaskType;
        this.validateSelf();
    }
}
