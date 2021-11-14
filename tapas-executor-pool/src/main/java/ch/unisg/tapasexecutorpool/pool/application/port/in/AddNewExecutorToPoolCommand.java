package ch.unisg.tapasexecutorpool.pool.application.port.in;

import ch.unisg.tapascommon.common.SelfValidating;
import ch.unisg.tapasexecutorpool.pool.adapter.in.formats.ExecutorJsonRepresentation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Value
public class AddNewExecutorToPoolCommand extends SelfValidating<AddNewExecutorToPoolCommand> {
    @Getter
    @NotNull
    ExecutorJsonRepresentation executorJsonRepresentation;

    public AddNewExecutorToPoolCommand(ExecutorJsonRepresentation executorJsonRepresentation) {
        this.executorJsonRepresentation = executorJsonRepresentation;
        this.validateSelf();
    }
}
