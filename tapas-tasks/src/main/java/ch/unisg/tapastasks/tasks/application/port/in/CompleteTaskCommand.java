package ch.unisg.tapastasks.tasks.application.port.in;

import ch.unisg.tapastasks.common.SelfValidating;
import ch.unisg.tapastasks.tasks.domain.Task.OutputData;
import ch.unisg.tapastasks.tasks.domain.Task.TaskId;
import ch.unisg.tapastasks.tasks.domain.Task.TaskResult;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class CompleteTaskCommand extends SelfValidating<CompleteTaskCommand> {
    @NotNull
    private final TaskId taskId;

    @NotNull
    private final OutputData outputData;

    public CompleteTaskCommand(TaskId taskId, OutputData outputData){
        this.taskId = taskId;
        this.outputData = outputData;
        this.validateSelf();
    }
}
