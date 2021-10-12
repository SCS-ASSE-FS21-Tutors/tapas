package ch.unisg.tapastasks.tasks.application.port.in;

import ch.unisg.tapastasks.common.SelfValidating;
import ch.unisg.tapastasks.tasks.domain.Task.TaskId;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class TaskStartedEvent extends SelfValidating<TaskStartedEvent> {

    @NotNull
    private final TaskId taskId;

    public TaskStartedEvent(TaskId taskId) {
        this.taskId = taskId;
        this.validateSelf();
    }



}
