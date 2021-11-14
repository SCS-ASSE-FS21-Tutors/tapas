package ch.unisg.executorpool.application.port.in;

import ch.unisg.common.SelfValidating;
import ch.unisg.executorpool.domain.ExecutorClass.ExecutorUri;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class RemoveExecutorFromExecutorPoolCommand extends SelfValidating<RemoveExecutorFromExecutorPoolCommand> {
    @NotNull
    private final ExecutorUri executorUri;

    public RemoveExecutorFromExecutorPoolCommand(ExecutorUri executorUri){
        this.executorUri = executorUri;
        this.validateSelf();
    }
}
