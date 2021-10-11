package ch.unisg.tapastasks.tasks.domain;

public class NewTaskAddedEvent {
    public Task task;

    public NewTaskAddedEvent(Task task) {
        this.task = task;
    }
}
