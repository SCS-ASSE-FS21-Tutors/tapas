package ch.unisg.tapas.auctionhouse.application.port.in;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.TaskJsonRepresentation;
import ch.unisg.tapas.common.SelfValidating;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class TaskWonCommand extends SelfValidating<TaskWonCommand> {
    public TaskWonCommand(TaskJsonRepresentation taskJson) {
        this.taskJson = taskJson;
        this.validateSelf();
    }

    @NotNull
    private final TaskJsonRepresentation taskJson;

}
