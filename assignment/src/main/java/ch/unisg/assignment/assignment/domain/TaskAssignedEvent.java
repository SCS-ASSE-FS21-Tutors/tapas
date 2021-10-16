package ch.unisg.assignment.assignment.domain;

public class TaskAssignedEvent {
    public String taskID;

    public TaskAssignedEvent(String taskID) {
        this.taskID = taskID;
    }
}
