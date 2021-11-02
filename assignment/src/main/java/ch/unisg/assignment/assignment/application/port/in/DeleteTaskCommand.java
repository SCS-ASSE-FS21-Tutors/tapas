package ch.unisg.assignment.assignment.application.port.in;

import javax.validation.constraints.NotNull;

import ch.unisg.assignment.assignment.domain.valueobject.ExecutorType;
import ch.unisg.common.validation.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper=false)
public class DeleteTaskCommand extends SelfValidating<ApplyForTaskCommand> {
    @NotNull
    private final String taskId;

    @NotNull
    private final ExecutorType taskType;

    public DeleteTaskCommand(String taskId, ExecutorType taskType) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.validateSelf();
    }
}
