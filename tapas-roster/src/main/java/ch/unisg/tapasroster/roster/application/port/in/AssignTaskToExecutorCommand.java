package ch.unisg.tapasroster.roster.application.port.in;

import ch.unisg.tapasroster.common.SelfValidating;
import ch.unisg.tapasroster.roster.domain.Task.TaskUri;
import ch.unisg.tapasroster.roster.domain.Task.TaskType;
import ch.unisg.tapasroster.roster.domain.Task.TaskId;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@EqualsAndHashCode(callSuper = true)
public class AssignTaskToExecutorCommand extends SelfValidating<AssignTaskToExecutorCommand> {
    @NotNull
    TaskId taskId;

    @NotNull
    TaskUri taskUri;

    @NotNull
    TaskType taskType;

    public AssignTaskToExecutorCommand(TaskId taskId, TaskType taskType, TaskUri taskUri) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.taskUri = taskUri;
        this.validateSelf();
    }
}