package ch.unisg.executorpool.application.port.in;

import ch.unisg.executorpool.domain.ExecutorPool;
import ch.unisg.tapastasks.common.SelfValidating;
import ch.unisg.executorpool.domain.ExecutorClass.ExecutorIp;
import ch.unisg.executorpool.domain.ExecutorClass.ExecutorPort;
import ch.unisg.executorpool.domain.ExecutorClass.ExecutorTaskType;
import lombok.Value;
import javax.validation.constraints.NotNull;

@Value
public class AddNewExecutorToExecutorPoolCommand extends SelfValidating<AddNewExecutorToExecutorPoolCommand> {
    @NotNull
    private final ExecutorIp executorIp;

    @NotNull
    private final ExecutorPort executorPort;

    @NotNull
    private final ExecutorTaskType executorTaskType;

    public AddNewExecutorToExecutorPoolCommand(ExecutorIp executorIp, ExecutorPort executorPort, ExecutorTaskType executorTaskType){
        this.executorIp = executorIp;
        this.executorPort = executorPort;
        this.executorTaskType = executorTaskType;
        this.validateSelf();
    }
}
