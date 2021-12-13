package ch.unisg.tapastasks.tasks.application.handler;

import org.springframework.context.ApplicationEvent;

public class TasksChangedEvent extends ApplicationEvent {

    public TasksChangedEvent(Object source) {
        super(source);
    }
}
