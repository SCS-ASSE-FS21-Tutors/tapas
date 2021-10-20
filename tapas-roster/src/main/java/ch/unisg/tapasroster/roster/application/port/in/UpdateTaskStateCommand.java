package ch.unisg.tapasroster.roster.application.port.in;

import ch.unisg.tapascommon.tasks.domain.Task;
import ch.unisg.tapasroster.common.SelfValidating;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class UpdateTaskStateCommand extends SelfValidating<UpdateTaskStateCommand> {
    @NotNull
    private final Task.TaskId taskId;

    @NotNull
    private final Task.TaskType taskType;

    public UpdateTaskStateCommand(Task.TaskId taskId, Task.TaskType taskType) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.validateSelf();
    }
}
