package ch.unisg.tapasexecutorcalc.executor.application.port.in;

import ch.unisg.tapasexecutorcalc.common.SelfValidating;
import ch.unisg.tapascommon.tasks.domain.Task;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Value
public class ExecuteTaskCommand extends SelfValidating<ExecuteTaskUseCase> {
    @NotNull
    Task task;

    public ExecuteTaskCommand(Task task) {
        this.task = task;
        this.validateSelf();
    }
}
