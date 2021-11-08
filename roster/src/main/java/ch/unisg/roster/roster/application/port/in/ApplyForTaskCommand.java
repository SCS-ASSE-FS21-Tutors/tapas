package ch.unisg.roster.roster.application.port.in;

import javax.validation.constraints.NotNull;

import ch.unisg.roster.roster.domain.valueobject.ExecutorType;
import ch.unisg.common.validation.SelfValidating;
import ch.unisg.common.valueobject.ExecutorURI;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper=false)
public class ApplyForTaskCommand extends SelfValidating<ApplyForTaskCommand>{

    @NotNull
    private final ExecutorType taskType;

    @NotNull
    private final ExecutorURI executorURI;

    public ApplyForTaskCommand(ExecutorType taskType, ExecutorURI executorURI) {
        this.taskType = taskType;
        this.executorURI = executorURI;
        this.validateSelf();
    }
}
