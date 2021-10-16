package ch.unisg.executorpool.application.port.in;

import ch.unisg.executorpool.domain.ExecutorClass;
import ch.unisg.tapastasks.common.SelfValidating;
import ch.unisg.executorpool.domain.ExecutorClass.ExecutorIp;
import ch.unisg.executorpool.domain.ExecutorClass.ExecutorPort;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class RemoveExecutorFromExecutorPoolCommand extends SelfValidating<RemoveExecutorFromExecutorPoolCommand> {
    @NotNull
    private final ExecutorIp executorIp;

    @NotNull
    private final ExecutorPort executorPort;

    public RemoveExecutorFromExecutorPoolCommand(ExecutorIp executorIp, ExecutorPort executorPort){
        this.executorIp = executorIp;
        this.executorPort = executorPort;
        this.validateSelf();
    }
}
