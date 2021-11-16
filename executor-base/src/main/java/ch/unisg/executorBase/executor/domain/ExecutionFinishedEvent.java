package ch.unisg.executorbase.executor.domain;

import lombok.Getter;

public class ExecutionFinishedEvent {

    @Getter
    private String taskID;

    @Getter
    private String outputData;

    @Getter
    private String status;

    public ExecutionFinishedEvent(String taskID, String outputData, String status) {
        this.taskID = taskID;
        this.outputData = outputData;
        this.status = status;
    }
}
