package ch.unisg.tapasroster.roster.application.port.out;

import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToExecutorCommand;

public interface TriggerAuctionForTaskEventPort {
    boolean triggerAuctionForTask(AssignTaskToExecutorCommand assignTaskToExecutorCommand);
}
