package ch.unisg.tapasexecutorpool.pool.application.port.in;
import ch.unisg.tapasexecutorpool.common.SelfValidating;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class NotifyTaskCompletionCommand extends SelfValidating<NotifyTaskCompletionCommand> {

    @NotNull
    private final String taskId;

    private final String outputData;

    public NotifyTaskCompletionCommand(String taskId, String outputData) {
        this.taskId = taskId;
        this.outputData = outputData;
        this.validateSelf();
    }
}
