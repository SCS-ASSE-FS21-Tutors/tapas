package ch.unisg.tapasexecutorcherrybot.cherrybot;

import lombok.Getter;

public class TaskFinishedEvent {

    @Getter
    private final String taskId;

    @Getter
    private final String taskListName;

    @Getter
    private final String result;

    public TaskFinishedEvent(String taskId, String taskListName, String result){
        this.taskId = taskId;
        this.taskListName = taskListName;
        this.result = result;
    }
}
