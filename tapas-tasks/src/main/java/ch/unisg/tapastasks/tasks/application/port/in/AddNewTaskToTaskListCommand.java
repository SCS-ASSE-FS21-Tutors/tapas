package ch.unisg.tapastasks.tasks.application.port.in;

import ch.unisg.tapastasks.common.SelfValidating;
import ch.unisg.tapastasks.tasks.domain.Task.TaskType;
import ch.unisg.tapastasks.tasks.domain.Task.TaskName;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class AddNewTaskToTaskListCommand extends SelfValidating<AddNewTaskToTaskListCommand> {
    @NotNull
    private final TaskName taskName;

    @NotNull
    private final TaskType taskType;

    public AddNewTaskToTaskListCommand(TaskName taskName, TaskType taskType) {
        this.taskName = taskName;
        this.taskType = taskType;
        this.validateSelf();
    }
}
