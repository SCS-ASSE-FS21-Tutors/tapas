package ch.unisg.tapastasks.tasks.application.port.in;

import ch.unisg.tapastasks.common.SelfValidating;
import ch.unisg.tapastasks.tasks.domain.Task.*;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class TaskExecutedEvent extends SelfValidating<TaskExecutedEvent> {

    @NotNull
    private final TaskId taskId;

    public TaskExecutedEvent(TaskId taskId) {
        this.taskId = taskId;
        this.validateSelf();
    }



}
