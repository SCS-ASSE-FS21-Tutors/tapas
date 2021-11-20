package ch.unisg.tapasexecutorpool.pool.application.port.out;

import ch.unisg.tapasexecutorpool.pool.domain.Task;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class UpdateTaskStatusCommand {
    @NotNull
    private final Task task;
    private final Task.TaskStatus newStatus;
    public UpdateTaskStatusCommand(Task task, Task.TaskStatus newStatus){
        this.task = task;
        this.newStatus = newStatus;
    }
}
