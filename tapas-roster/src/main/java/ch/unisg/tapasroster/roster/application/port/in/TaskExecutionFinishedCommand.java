package ch.unisg.tapasroster.roster.application.port.in;

import ch.unisg.tapasroster.common.SelfValidating;
import ch.unisg.tapasroster.roster.domain.Task;
import ch.unisg.tapasroster.roster.domain.TaskFinishedReply;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Value
public class TaskExecutionFinishedCommand extends SelfValidating<TaskExecutionFinishedCommand> {

    @NotNull
    TaskFinishedReply taskFinishedReply;

    public TaskExecutionFinishedCommand(TaskFinishedReply taskFinishedReply) {
        this.taskFinishedReply = taskFinishedReply;
        this.validateSelf();
    }
}
