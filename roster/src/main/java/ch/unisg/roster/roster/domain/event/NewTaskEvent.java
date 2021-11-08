package ch.unisg.roster.roster.domain.event;

import ch.unisg.roster.roster.domain.valueobject.ExecutorType;

public class NewTaskEvent {
    public final ExecutorType taskType;

    public NewTaskEvent(ExecutorType taskType) {
        this.taskType = taskType;
    }
}
