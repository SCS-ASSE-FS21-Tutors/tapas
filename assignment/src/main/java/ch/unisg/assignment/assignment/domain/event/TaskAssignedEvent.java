package ch.unisg.assignment.assignment.domain.event;

public class TaskAssignedEvent {
    public String taskID;

    public TaskAssignedEvent(String taskID) {
        this.taskID = taskID;
    }
}
