package ch.unisg.tapasroster.roster.application.port.in;

import ch.unisg.tapasroster.common.SelfValidating;
import ch.unisg.tapasroster.roster.domain.Task.TaskType;
import ch.unisg.tapasroster.roster.domain.Task.TaskId;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class AssignTaskToExecutorInExecutorPoolCommand extends SelfValidating<AssignTaskToExecutorInExecutorPoolCommand> {
    @NotNull
    private final TaskId taskId;

    @NotNull
    private final TaskType taskType;

    public AssignTaskToExecutorInExecutorPoolCommand(TaskId taskId, TaskType taskType) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.validateSelf();
    }
}