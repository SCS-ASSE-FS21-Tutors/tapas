package ch.unisg.tapastasks.tasks.application.port.in;

import ch.unisg.tapastasks.common.SelfValidating;
import ch.unisg.tapastasks.tasks.domain.Task;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Value
public class AddNewTaskToTaskListCommand extends SelfValidating<AddNewTaskToTaskListCommand> {
    @NotNull
    Task.TaskName taskName;

    @NotNull
    Task.TaskType taskType;

    @Getter
    @NotNull
    Optional<Task.InputData> inputData;

    @Getter
    @NotNull
    Optional<Task.OriginalTaskUri> originalTaskUri;

    public AddNewTaskToTaskListCommand(
        Task.TaskName taskName,
        Task.TaskType taskType,
        Optional<Task.InputData> inputData,
        Optional<Task.OriginalTaskUri> originalTaskUri)
    {
        this.taskName = taskName;
        this.taskType = taskType;
        this.inputData = inputData;
        this.originalTaskUri = originalTaskUri;

        this.validateSelf();
    }
}
