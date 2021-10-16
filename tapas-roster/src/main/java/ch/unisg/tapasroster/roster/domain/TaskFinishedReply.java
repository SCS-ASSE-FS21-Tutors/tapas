package ch.unisg.tapasroster.roster.domain;

import lombok.Getter;

public class TaskFinishedReply {

    @Getter
    private final String taskId;

    @Getter
    private final String taskListName;

    @Getter
    private final String result;

    public TaskFinishedReply(String taskId, String taskListName, String result){
        this.taskId = taskId;
        this.taskListName = taskListName;
        this.result = result;
    }

    @Override
    public String toString() {
        return "TaskFinishedReply{" +
                "taskId='" + taskId + '\'' +
                ", taskListName='" + taskListName + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
