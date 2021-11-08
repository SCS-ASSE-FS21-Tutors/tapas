package ch.unisg.tapastasks.tasks.application.port.out;

import ch.unisg.tapastasks.tasks.domain.DeleteTaskEvent;

public interface CanTaskBeDeletedPort {
    void canTaskBeDeletedEvent(DeleteTaskEvent event);
}
