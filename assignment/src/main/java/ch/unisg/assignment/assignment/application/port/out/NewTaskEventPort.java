package ch.unisg.assignment.assignment.application.port.out;

import ch.unisg.assignment.assignment.domain.event.NewTaskEvent;

public interface NewTaskEventPort {
    /**
    *   Publishes the new task event.
    *   @return void
    **/
    void publishNewTaskEvent(NewTaskEvent event);
}
