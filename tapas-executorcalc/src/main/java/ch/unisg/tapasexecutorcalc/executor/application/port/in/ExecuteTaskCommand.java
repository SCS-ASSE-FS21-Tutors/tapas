package ch.unisg.tapasexecutorcalc.executor.application.port.in;

import ch.unisg.tapasexecutorcalc.common.SelfValidating;
import ch.unisg.tapasexecutorcalc.executor.domain.Task;
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
