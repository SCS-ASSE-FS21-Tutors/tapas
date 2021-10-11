package ch.unisg.executor1.executor.application.port.in;

import ch.unisg.executor1.common.SelfValidating;
import ch.unisg.executor1.executor.domain.ExecutorType;

import javax.validation.constraints.NotNull;

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
