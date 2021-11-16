package ch.unisg.tapastasks.tasks.application.port.out;

import javax.validation.constraints.NotNull;

import ch.unisg.tapastasks.common.SelfValidating;
import ch.unisg.tapastasks.tasks.domain.Task;
import lombok.Getter;
import lombok.Value;

@Value
public class ExternalTaskExecutedEvent extends SelfValidating<ExternalTaskExecutedEvent> {
    @NotNull
    private final Task.TaskId taskId;

    @Getter
    private final Task.OriginalTaskUri originalTaskUri;

    @Getter
    private final Task.OutputData outputData;

    public ExternalTaskExecutedEvent(Task.TaskId taskId, Task.OriginalTaskUri originalTaskUri, Task.OutputData outputData) {
        this.taskId = taskId;
        this.originalTaskUri = originalTaskUri;
        this.outputData = outputData;

        this.validateSelf();
    }
}
