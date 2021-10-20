package ch.unisg.tapasroster.roster.application.port.in;

import ch.unisg.tapascommon.tasks.domain.Task;
import ch.unisg.tapasroster.common.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Value
public class ScheduleTaskCommand extends SelfValidating<ScheduleTaskCommand> {
    @NotNull
    Task task;

    public ScheduleTaskCommand(Task task) {
        this.task = task;
        this.validateSelf();
    }
}
