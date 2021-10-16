package ch.unisg.assignment.assignment.domain;

public class NewTaskEvent {
    public String taskType;

    public NewTaskEvent(String taskType) {
        this.taskType = taskType;
    }
}
