package ch.unisg.assignment.assignment.application.port.in;

import javax.validation.constraints.NotNull;

import ch.unisg.assignment.common.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper=false)
public class TaskCompletedCommand extends SelfValidating<TaskCompletedCommand>{

    @NotNull
    private final String taskID;

    @NotNull
    private final String taskType;

    @NotNull
    private final String taskStatus;

    @NotNull
    private final String taskResult;

    public TaskCompletedCommand(String taskID, String taskType, String taskStatus, String taskResult) {
        this.taskID = taskID;
        this.taskType = taskType;
        this.taskStatus = taskStatus;
        this.taskResult = taskResult;
        this.validateSelf();
    }

}
