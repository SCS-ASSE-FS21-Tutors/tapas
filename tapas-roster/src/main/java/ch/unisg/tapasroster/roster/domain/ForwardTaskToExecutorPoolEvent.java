package ch.unisg.tapasroster.roster.domain;

import ch.unisg.tapascommon.common.SelfValidating;
import ch.unisg.tapascommon.tasks.domain.Task;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Value
public class ForwardTaskToExecutorPoolEvent extends SelfValidating<ForwardTaskToExecutorPoolEvent> {

    @Getter
    @NotNull
    Task task;

    public ForwardTaskToExecutorPoolEvent(Task task) {
        this.task = task;
        this.validateSelf();
    }
}
