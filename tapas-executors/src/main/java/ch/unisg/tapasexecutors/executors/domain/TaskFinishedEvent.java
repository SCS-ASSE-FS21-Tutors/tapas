package ch.unisg.tapasexecutors.executors.domain;

import lombok.Getter;

public class TaskFinishedEvent {

    @Getter
    private final String taskId;

    @Getter
    private final String taskListName;

    @Getter
    private final String result;

    public TaskFinishedEvent(Task task, String result){
        this.taskId = task.getTaskId().getValue();
        this.taskListName = task.getTaskListName().getValue();
        this.result = result == null ? "" : result;
    }
}
