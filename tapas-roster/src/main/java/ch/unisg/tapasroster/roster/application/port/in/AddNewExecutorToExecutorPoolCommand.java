package ch.unisg.tapasroster.roster.application.port.in;

import ch.unisg.tapasroster.common.SelfValidating;
import ch.unisg.tapasroster.roster.domain.Executor.ExecutorName;
import ch.unisg.tapasroster.roster.domain.Task;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Value
public class AddNewExecutorToExecutorPoolCommand extends SelfValidating<AddNewExecutorToExecutorPoolCommand> {
    @NotNull
    ExecutorName executorName;

    @NotNull
    Task.TaskType taskType;

    public AddNewExecutorToExecutorPoolCommand(ExecutorName executorName, Task.TaskType taskType) {
        this.executorName = executorName;
        this.taskType = taskType;
        this.validateSelf();
    }
}
