package ch.unisg.tapasexecutordigital.executordigital.application.port.in;

import ch.unisg.tapasexecutordigital.common.SelfValidating;
import ch.unisg.tapasexecutordigital.executordigital.domain.Task.TaskName;
import ch.unisg.tapasexecutordigital.executordigital.domain.Task.TaskType;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class AssignTaskCommand extends SelfValidating<AssignTaskUseCase> {
    @NotNull
    private final TaskName taskName;

    @NotNull
    private final TaskType taskType;

    public AssignTaskCommand(TaskName taskName, TaskType taskType) {
        this.taskName = taskName;
        this.taskType = taskType;
        this.validateSelf();
    }
}