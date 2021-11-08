package ch.unisg.tapastasks.tasks.domain;

public class DeleteTaskEvent {
    public String taskId;
    public String taskUri;

    public DeleteTaskEvent(String taskId, String taskUri){
        this.taskId = taskId;
        this.taskUri = taskUri;
    }
}
