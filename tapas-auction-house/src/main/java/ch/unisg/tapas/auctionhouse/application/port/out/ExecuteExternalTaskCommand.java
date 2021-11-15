package ch.unisg.tapas.auctionhouse.application.port.out;

import ch.unisg.tapas.auctionhouse.domain.Task;
import ch.unisg.tapas.common.SelfValidating;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ExecuteExternalTaskCommand extends SelfValidating <ExecuteExternalTaskCommand> {
    @NotNull
    private final Task task;

    public ExecuteExternalTaskCommand(Task task){
        this.task = task;
        this.validateSelf();
    }

}
