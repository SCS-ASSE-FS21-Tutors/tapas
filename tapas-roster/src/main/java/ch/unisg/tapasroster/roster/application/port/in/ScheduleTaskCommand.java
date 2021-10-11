package ch.unisg.tapasroster.roster.application.port.in;

import ch.unisg.tapasroster.common.SelfValidating;
import ch.unisg.tapasroster.roster.domain.Task;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ScheduleTaskCommand extends SelfValidating<ScheduleTaskCommand> {
    @NotNull
    private final Task task;

    public ScheduleTaskCommand(Task task) {
        this.task = task;
        this.validateSelf();
    }
}
