package ch.unisg.tapasroster.executorpool.application.port.in;

import ch.unisg.tapasroster.common.SelfValidating;
import ch.unisg.tapasroster.executorpool.domain.Executor;
import ch.unisg.tapasroster.roster.domain.Task;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Value
public class AddNewExecutorToExecutorPoolCommand extends SelfValidating<AddNewExecutorToExecutorPoolCommand> {
    @NotNull
    Executor.ExecutorName executorName;

    @NotNull
    Task.TaskType taskType;

    public AddNewExecutorToExecutorPoolCommand(Executor.ExecutorName executorName, Task.TaskType taskType) {
        this.executorName = executorName;
        this.taskType = taskType;
        this.validateSelf();
    }
}
