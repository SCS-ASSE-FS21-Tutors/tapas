package ch.unisg.roster.roster.application.port.in;

import javax.validation.constraints.NotNull;

import ch.unisg.common.validation.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper=false)
public class TaskCompletedCommand extends SelfValidating<TaskCompletedCommand>{

    @NotNull
    private final String taskID;

    @NotNull
    private final String taskStatus;

    @NotNull
    private final String taskResult;

    public TaskCompletedCommand(String taskID, String taskStatus, String taskResult) {
        this.taskID = taskID;
        this.taskStatus = taskStatus;
        this.taskResult = taskResult;
        this.validateSelf();
    }

}
