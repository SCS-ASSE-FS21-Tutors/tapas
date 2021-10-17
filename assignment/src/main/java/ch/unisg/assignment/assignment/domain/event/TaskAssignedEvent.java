package ch.unisg.assignment.assignment.domain.event;

public class TaskAssignedEvent {
    public final String taskID;

    public TaskAssignedEvent(String taskID) {
        this.taskID = taskID;
    }
}
