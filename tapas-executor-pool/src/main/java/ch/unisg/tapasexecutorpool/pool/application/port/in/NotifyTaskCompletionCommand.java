package ch.unisg.tapasexecutorpool.pool.application.port.in;
import ch.unisg.tapasexecutorpool.common.SelfValidating;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class NotifyTaskCompletionCommand extends SelfValidating<NotifyTaskCompletionCommand> {

    @NotNull
    private final String taskId;

    public NotifyTaskCompletionCommand(String taskId) {
        this.taskId = taskId;
        this.validateSelf();
    }
}
