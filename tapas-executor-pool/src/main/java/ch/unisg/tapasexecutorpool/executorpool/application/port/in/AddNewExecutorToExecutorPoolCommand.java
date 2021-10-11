package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import ch.unisg.tapasexecutorpool.common.SelfValidating;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor.TaskType;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor.ExecutorName;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class AddNewExecutorToExecutorPoolCommand extends SelfValidating<AddNewExecutorToExecutorPoolCommand> {
    @NotNull
    private final ExecutorName executorName;

    @NotNull
    private final TaskType taskType;

    public AddNewExecutorToExecutorPoolCommand(ExecutorName executorName, TaskType taskType) {
        this.executorName = executorName;
        this.taskType = taskType;
        this.validateSelf();
    }
}
