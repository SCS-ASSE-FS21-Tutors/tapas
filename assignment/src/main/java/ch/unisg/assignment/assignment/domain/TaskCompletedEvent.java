package ch.unisg.assignment.assignment.domain;

public class TaskCompletedEvent {
    public String taskID;

    public String status;

    public String result;

    public TaskCompletedEvent(String taskID, String status, String result) {
        this.taskID = taskID;
        this.status = status;
        this.result = result;
    }
}
