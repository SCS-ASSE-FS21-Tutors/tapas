package ch.unisg.tapastasks.tasks.application.port.in;

import ch.unisg.tapastasks.common.SelfValidating;
import ch.unisg.tapastasks.tasks.domain.Task.TaskId;
import ch.unisg.tapastasks.tasks.domain.Task.TaskResult;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class CompleteTaskCommand extends SelfValidating<CompleteTaskCommand> {
    @NotNull
    private final TaskId taskId;

    @NotNull
    private final TaskResult taskResult;

    public CompleteTaskCommand(TaskId taskId, TaskResult taskResult){
        this.taskId = taskId;
        this.taskResult = taskResult;
        this.validateSelf();
    }
}
