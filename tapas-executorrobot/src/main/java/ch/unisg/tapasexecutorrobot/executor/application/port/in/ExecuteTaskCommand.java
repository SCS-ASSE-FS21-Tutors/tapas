package ch.unisg.tapasexecutorrobot.executor.application.port.in;

import ch.unisg.tapasexecutorrobot.common.SelfValidating;
import ch.unisg.tapasexecutorrobot.executor.application.port.in.ExecuteTaskUseCase;
import ch.unisg.tapasexecutorrobot.executor.domain.Task;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ExecuteTaskCommand extends SelfValidating<ExecuteTaskUseCase> {
    @NotNull
    private final Task task;

    public ExecuteTaskCommand(Task task) {
        this.task = task;
        this.validateSelf();
    }
}
