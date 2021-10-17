package ch.unisg.tapastasks.tasks.application.port.out;

import ch.unisg.tapastasks.tasks.domain.NewTaskAddedEvent;
import ch.unisg.tapastasks.tasks.domain.Task;

public interface NewTaskAddedEventPort {

    void publishNewTaskAddedEvent(Task task);
}
