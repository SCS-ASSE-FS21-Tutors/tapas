package ch.unisg.tapasexecutorpool.pool.application.port.in;

import ch.unisg.tapascommon.common.SelfValidating;
import ch.unisg.tapascommon.pool.adapter.in.formats.ExecutorJsonRepresentation;
import ch.unisg.tapascommon.pool.domain.Executor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Value
public class AddNewExecutorToPoolCommand extends SelfValidating<AddNewExecutorToPoolCommand> {
    @Getter
    @NotNull
    String executorId;

    @Getter
    @NotNull
    String executorName;

    @Getter
    @NotNull
    String executorType;

    @Getter
    @NotNull
    String executorAddress;

    public AddNewExecutorToPoolCommand(
            String executorId,
            String executorName,
            String executorType,
            String executorAddress
    ) {
        this.executorId = executorId;
        this.executorName = executorName;
        this.executorType = executorType;
        this.executorAddress = executorAddress;
        this.validateSelf();
    }
}
