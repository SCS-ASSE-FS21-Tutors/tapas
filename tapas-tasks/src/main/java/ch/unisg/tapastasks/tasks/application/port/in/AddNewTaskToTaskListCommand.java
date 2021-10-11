package ch.unisg.tapastasks.tasks.application.port.in;

import ch.unisg.tapastasks.common.SelfValidating;
import ch.unisg.tapastasks.tasks.domain.Task.TaskType;
import ch.unisg.tapastasks.tasks.domain.Task.TaskName;
import ch.unisg.tapastasks.tasks.domain.Task.TaskPayload;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class AddNewTaskToTaskListCommand extends SelfValidating<AddNewTaskToTaskListCommand> {
    @NotNull
    private final TaskName taskName;

    @NotNull
    private final TaskType taskType;

    @NotNull
    private final TaskPayload taskPayload;

    public AddNewTaskToTaskListCommand(TaskName taskName, TaskType taskType, TaskPayload taskPayload) {
        this.taskName = taskName;
        this.taskType = taskType;
        this.taskPayload = taskPayload;
        this.validateSelf();
    }
}
