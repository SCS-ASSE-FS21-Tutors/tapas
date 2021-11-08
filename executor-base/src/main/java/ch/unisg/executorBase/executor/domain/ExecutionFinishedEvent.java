package ch.unisg.executorbase.executor.domain;

import lombok.Getter;

public class ExecutionFinishedEvent {

    @Getter
    private String taskID;

    @Getter
    private String result;

    @Getter
    private String status;

    public ExecutionFinishedEvent(String taskID, String result, String status) {
        this.taskID = taskID;
        this.result = result;
        this.status = status;
    }
}
