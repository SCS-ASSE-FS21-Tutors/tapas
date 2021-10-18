package ch.unisg.tapastasks.tasks.application.port.in;

import ch.unisg.tapastasks.common.SelfValidating;
import ch.unisg.tapastasks.tasks.domain.Task.TaskId;
import ch.unisg.tapastasks.tasks.domain.Task.TaskType;

import javax.validation.constraints.NotNull;

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
