package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import ch.unisg.tapasexecutorpool.executorpool.domain.Task;
import ch.unisg.tapasexecutorpool.common.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Value
public class AssignTaskToRosterCommand extends SelfValidating<AssignTaskToRosterCommand> {
    @NotNull
    Task task;

    public AssignTaskToRosterCommand(Task task) {
        this.task = task;
        this.validateSelf();
    }
}
