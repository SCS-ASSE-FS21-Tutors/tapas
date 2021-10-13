package ch.unisg.tapastasks.tasks.domain;

/**This is a domain event (these are usually much fatter)**/
public class NewTaskAddedEvent {
    public String taskName;
    public String taskListName;
    public String taskType;
    public String taskId;

    public NewTaskAddedEvent(String taskName, String taskListName, String taskType, String taskId) {
        this.taskName = taskName;
        this.taskListName = taskListName;
        this.taskType = taskType;
        this.taskId = taskId;
    }
}
