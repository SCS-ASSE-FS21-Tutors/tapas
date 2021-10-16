package ch.unisg.assignment.assignment.application.port.out;

import ch.unisg.assignment.assignment.domain.TaskCompletedEvent;

public interface TaskCompletedEventPort {
    void publishTaskCompleted(TaskCompletedEvent event);
}
