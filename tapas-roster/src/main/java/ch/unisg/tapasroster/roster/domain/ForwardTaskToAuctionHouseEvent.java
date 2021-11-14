package ch.unisg.tapasroster.roster.domain;

import ch.unisg.tapascommon.common.SelfValidating;
import ch.unisg.tapascommon.tasks.domain.Task;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Value
public class ForwardTaskToAuctionHouseEvent extends SelfValidating<ForwardTaskToAuctionHouseEvent> {

    @Getter
    @NotNull
    Task task;

    @Getter
    @NotNull
    long deadline;

    public ForwardTaskToAuctionHouseEvent(Task task, long deadline) {
        this.task = task;
        this.deadline = deadline;
        this.validateSelf();
    }
}
