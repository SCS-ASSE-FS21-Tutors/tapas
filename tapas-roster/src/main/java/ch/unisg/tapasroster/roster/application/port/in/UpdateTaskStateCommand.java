package ch.unisg.tapasroster.roster.application.port.in;

import ch.unisg.tapasroster.common.SelfValidating;
import ch.unisg.tapasroster.roster.domain.Task.TaskId;
import ch.unisg.tapasroster.roster.domain.Task.TaskType;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class UpdateTaskStateCommand extends SelfValidating<UpdateTaskStateCommand> {
    @NotNull
    private final TaskId taskId;

    @NotNull
    private final TaskType taskType;

    public UpdateTaskStateCommand(TaskId taskId, TaskType taskType) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.validateSelf();
    }
}
