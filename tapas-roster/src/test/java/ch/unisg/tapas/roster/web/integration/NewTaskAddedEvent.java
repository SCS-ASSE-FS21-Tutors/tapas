package ch.unisg.tapas.roster.web.integration;

/**This is a domain event (these are usually much fatter)**/
public class NewTaskAddedEvent {
    public String taskName;
    public String taskListName;

    public NewTaskAddedEvent(String taskName, String taskListName) {
        this.taskName = taskName;
        this.taskListName = taskListName;
    }
}
