package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import ch.unisg.tapasexecutorpool.common.SelfValidating;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor.ExecutorName;
import ch.unisg.tapasexecutorpool.executorpool.domain.Task;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class AddNewExecutorToExecutorPoolCommand extends SelfValidating<AddNewExecutorToExecutorPoolCommand> {
    @NotNull
    private final ExecutorName executorName;

    @NotNull
    private final Task.TaskType taskType;

    public AddNewExecutorToExecutorPoolCommand(ExecutorName executorName, Task.TaskType taskType) {
        this.executorName = executorName;
        this.taskType = taskType;
        this.validateSelf();
    }
}
