package ch.unisg.roster.roster.application.port.out;

import ch.unisg.roster.roster.domain.event.NewTaskEvent;

public interface NewTaskEventPort {
    /**
    *   Publishes the new task event.
    *   @return void
    **/
    void publishNewTaskEvent(NewTaskEvent event);
}
