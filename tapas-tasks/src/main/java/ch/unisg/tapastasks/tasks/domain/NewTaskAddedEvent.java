package ch.unisg.tapastasks.tasks.domain;

/**This is a domain event (these are usually much fatter)**/
public class NewTaskAddedEvent {
    public Task task;

    public NewTaskAddedEvent(Task task) {
        this.task = task;
    }
}
