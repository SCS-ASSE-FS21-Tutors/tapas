package ch.unisg.tapastasks.tasks.application.port.in;

import ch.unisg.tapastasks.common.SelfValidating;
import ch.unisg.tapastasks.tasks.domain.Task.TaskId;
import ch.unisg.tapastasks.tasks.domain.Task.OriginalTaskUri;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class DeleteTaskCommand extends SelfValidating<DeleteTaskCommand> {
    @NotNull
    private final TaskId taskId;

    @NotNull
    private final OriginalTaskUri taskUri;

    public DeleteTaskCommand(TaskId taskId, OriginalTaskUri taskUri){
        this.taskId=taskId;
        this.taskUri = taskUri;
        this.validateSelf();
    }
}
