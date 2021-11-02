package ch.unisg.assignment.assignment.application.port.out;

import ch.unisg.assignment.assignment.domain.event.TaskAssignedEvent;

public interface TaskAssignedEventPort {
    /**
    *   Publishes the task assigned event.
    *   @return void
    **/
    void publishTaskAssignedEvent(TaskAssignedEvent taskAssignedEvent);
}
