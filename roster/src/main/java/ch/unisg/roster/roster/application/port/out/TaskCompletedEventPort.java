package ch.unisg.roster.roster.application.port.out;

import ch.unisg.roster.roster.domain.event.TaskCompletedEvent;

public interface TaskCompletedEventPort {
    /**
    *   Publishes the task completed event.
    *   @return void
    **/
    void publishTaskCompleted(TaskCompletedEvent event);
}
