package ch.unisg.executorbase.executor.application.port.in;

import javax.validation.constraints.NotNull;

import ch.unisg.common.validation.SelfValidating;
import ch.unisg.executorbase.executor.domain.ExecutorType;
import lombok.Value;

@Value
public class TaskAvailableCommand extends SelfValidating<TaskAvailableCommand> {

    @NotNull
    private final ExecutorType taskType;

    public TaskAvailableCommand(ExecutorType taskType) {
        this.taskType = taskType;
        this.validateSelf();
    }
}
