package ch.unisg.assignment.assignment.domain.event;

public class TaskCompletedEvent {
    public final String taskID;

    public final String status;

    public final String result;

    public TaskCompletedEvent(String taskID, String status, String result) {
        this.taskID = taskID;
        this.status = status;
        this.result = result;
    }
}
