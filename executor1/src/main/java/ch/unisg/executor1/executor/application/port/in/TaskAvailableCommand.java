package ch.unisg.executor1.executor.application.port.in;

import ch.unisg.executor1.common.SelfValidating;

import javax.validation.constraints.NotNull;

import lombok.Value;

@Value
public class TaskAvailableCommand extends SelfValidating<TaskAvailableCommand> {
    
    @NotNull
    private final String taskType;

    public TaskAvailableCommand(String taskType) {
        this.taskType = taskType;
        this.validateSelf();
    }
}
