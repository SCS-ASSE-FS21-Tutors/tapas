package ch.unisg.assignment.assignment.application.port.out;

import ch.unisg.assignment.assignment.domain.event.TaskCompletedEvent;

public interface TaskCompletedEventPort {
    /**
    *   Publishes the task completed event.
    *   @return void
    **/
    void publishTaskCompleted(TaskCompletedEvent event);
}
