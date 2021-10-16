package ch.unisg.assignment.assignment.application.port.out;

import ch.unisg.assignment.assignment.domain.NewTaskEvent;

public interface NewTaskEventPort {
    void publishNewTaskEvent(NewTaskEvent event);
}
