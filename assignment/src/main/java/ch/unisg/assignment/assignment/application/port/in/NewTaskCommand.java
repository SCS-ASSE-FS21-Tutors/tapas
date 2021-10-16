package ch.unisg.assignment.assignment.application.port.in;

import javax.validation.constraints.NotNull;

import ch.unisg.assignment.common.SelfValidating;

import lombok.Value;

@Value
public class NewTaskCommand extends SelfValidating<NewTaskCommand> {

    @NotNull
    private final String taskID;

    @NotNull
    private final String taskType;

    public NewTaskCommand(String taskID, String taskType) {
        this.taskID = taskID;
        this.taskType = taskType;
        this.validateSelf();
    }
}
