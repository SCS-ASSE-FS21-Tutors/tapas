package ch.unisg.tapas.auctionhouse.application.port.out;

import ch.unisg.tapas.auctionhouse.domain.Task;
import ch.unisg.tapas.common.SelfValidating;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class UpdateExternalTaskCommand extends SelfValidating <UpdateExternalTaskCommand> {
    @NotNull
    private final Task task;
    private final Task.TaskStatus newStatus;

    public UpdateExternalTaskCommand(Task task, Task.TaskStatus newStatus){
        this.task = task;
        this.newStatus = newStatus;
        this.validateSelf();
    }

}
