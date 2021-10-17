package ch.unisg.assignment.assignment.application.port.out;

import ch.unisg.assignment.assignment.domain.event.TaskCompletedEvent;

public interface TaskCompletedEventPort {
    void publishTaskCompleted(TaskCompletedEvent event);
}
