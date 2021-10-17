package ch.unisg.assignment.assignment.domain.event;

import ch.unisg.assignment.assignment.domain.valueobject.ExecutorType;

public class NewTaskEvent {
    public final ExecutorType taskType;

    public NewTaskEvent(ExecutorType taskType) {
        this.taskType = taskType;
    }
}
