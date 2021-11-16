package ch.unisg.tapastasks.tasks.domain;

/**This is a domain event (these are usually much fatter)**/
public class NewTaskAddedEvent {
    public String taskName;
    public String taskListName;
    public String taskId;
    public String taskType;
    public String inputData;

    public NewTaskAddedEvent(String taskName, String taskListName, String taskId, String taskType, String inputData) {
        this.taskName = taskName;
        this.taskListName = taskListName;
        this.taskId = taskId;
        this.taskType = taskType;
        this.inputData = inputData;
    }

}
