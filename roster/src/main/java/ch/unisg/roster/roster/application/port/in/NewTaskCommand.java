package ch.unisg.roster.roster.application.port.in;

import javax.validation.constraints.NotNull;

import ch.unisg.roster.roster.domain.valueobject.ExecutorType;
import ch.unisg.common.validation.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper=false)
public class NewTaskCommand extends SelfValidating<NewTaskCommand> {

    @NotNull
    private final String taskID;

    @NotNull
    private final ExecutorType taskType;

    @NotNull
    private final String inputData;

    public NewTaskCommand(String taskID, ExecutorType taskType, String inputData) {
        this.taskID = taskID;
        this.taskType = taskType;
        this.inputData = inputData;
        this.validateSelf();
    }
}
