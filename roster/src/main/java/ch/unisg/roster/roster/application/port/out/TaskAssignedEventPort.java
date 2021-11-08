package ch.unisg.roster.roster.application.port.out;

import ch.unisg.roster.roster.domain.event.TaskAssignedEvent;

public interface TaskAssignedEventPort {
    /**
    *   Publishes the task assigned event.
    *   @return void
    **/
    void publishTaskAssignedEvent(TaskAssignedEvent taskAssignedEvent);
}
