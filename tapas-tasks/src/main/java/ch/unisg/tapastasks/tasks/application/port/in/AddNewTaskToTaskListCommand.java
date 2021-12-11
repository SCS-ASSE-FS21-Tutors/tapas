package ch.unisg.tapastasks.tasks.application.port.in;

import ch.unisg.tapastasks.common.SelfValidating;
import ch.unisg.tapastasks.tasks.domain.Task;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class AddNewTaskToTaskListCommand extends SelfValidating<AddNewTaskToTaskListCommand> {
    @NotNull
    private final Task.TaskName taskName;

    @NotNull
    private final Task.TaskType taskType;

    @NotNull
    private final Task.InputData inputData;


    public AddNewTaskToTaskListCommand(Task.TaskName taskName, Task.TaskType taskType, Task.InputData inputData) {
        this.taskName = taskName;
        this.taskType = taskType;
        this.inputData = inputData;

        this.validateSelf();
    }
}
