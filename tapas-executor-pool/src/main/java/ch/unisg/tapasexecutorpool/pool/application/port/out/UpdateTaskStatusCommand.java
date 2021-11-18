package ch.unisg.tapasexecutorpool.pool.application.port.out;

import ch.unisg.tapasexecutorpool.pool.domain.Task;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class UpdateTaskStatusCommand {
    @NotNull
    private final Task.TaskId taskId;
    private final Task.TaskStatus newStatus;
    public UpdateTaskStatusCommand(Task.TaskId taskId, Task.TaskStatus newStatus){
        this.taskId = taskId;
        this.newStatus = newStatus;
    }
}
