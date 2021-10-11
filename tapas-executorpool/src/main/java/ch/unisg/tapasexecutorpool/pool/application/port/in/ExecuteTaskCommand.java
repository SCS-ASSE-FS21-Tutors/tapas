package ch.unisg.tapasexecutorpool.pool.application.port.in;

import ch.unisg.tapasexecutorpool.common.SelfValidating;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ExecuteTaskCommand extends SelfValidating<ExecuteTaskCommand> {
    @NotNull
    private final Task task;

    public ExecuteTaskCommand(Task task) {
        this.task = task;
        this.validateSelf();
    }
}
