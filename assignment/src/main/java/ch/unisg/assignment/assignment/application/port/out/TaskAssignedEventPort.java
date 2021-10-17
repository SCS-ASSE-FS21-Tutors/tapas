package ch.unisg.assignment.assignment.application.port.out;

import ch.unisg.assignment.assignment.domain.event.TaskAssignedEvent;

public interface TaskAssignedEventPort {
    void publishTaskAssignedEvent(TaskAssignedEvent taskAssignedEvent);
}
