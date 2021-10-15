package ch.unisg.tapasexecutorpool.pool.application.port.in;
import ch.unisg.tapasexecutorpool.common.SelfValidating;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class AssignTaskCommand extends SelfValidating<AssignTaskCommand> {

    @NotNull
    private final Task.TaskId taskId;

    @NotNull
    private final Task.TaskName taskName;

    @NotNull
    private final Task.TaskType taskType;

    public AssignTaskCommand(Task.TaskId taskId, Task.TaskName taskName, Task.TaskType taskType) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskType = taskType;
        this.validateSelf();
    }
}
