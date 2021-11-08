package ch.unisg.tapastasks.tasks.application.port.in;

import ch.unisg.tapastasks.common.SelfValidating;
import ch.unisg.tapastasks.tasks.domain.Task;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Value
public class AddNewTaskToTaskListCommand extends SelfValidating<AddNewTaskToTaskListCommand> {
    @NotNull
    private final Task.TaskName taskName;

    @NotNull
    private final Task.TaskType taskType;

    @Getter
    private final Optional<Task.OriginalTaskUri> originalTaskUri;

    public AddNewTaskToTaskListCommand(Task.TaskName taskName, Task.TaskType taskType,
            Optional<Task.OriginalTaskUri> originalTaskUri) {
        this.taskName = taskName;
        this.taskType = taskType;
        this.originalTaskUri = originalTaskUri;

        this.validateSelf();
    }
}
