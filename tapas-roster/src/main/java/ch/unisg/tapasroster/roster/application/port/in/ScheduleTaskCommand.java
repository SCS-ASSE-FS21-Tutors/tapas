package ch.unisg.tapasroster.roster.application.port.in;

import ch.unisg.tapasroster.common.SelfValidating;
import ch.unisg.tapasroster.roster.domain.Task.TaskId;
import ch.unisg.tapasroster.roster.domain.Task.TaskName;
import ch.unisg.tapasroster.roster.domain.Task.TaskType;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ScheduleTaskCommand extends SelfValidating<ScheduleTaskCommand> {
    @NotNull
    private final TaskId taskId;

    @NotNull
    private final TaskName taskName;

    @NotNull
    private final TaskType taskType;

    public ScheduleTaskCommand(TaskId taskId, TaskName taskName, TaskType taskType) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskType = taskType;
        this.validateSelf();
    }
}
