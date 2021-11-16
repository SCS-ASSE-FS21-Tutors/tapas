package ch.unisg.roster.roster.domain;

import ch.unisg.roster.roster.domain.valueobject.ExecutorType;
import lombok.Getter;
import lombok.Setter;

public class Task {

    @Getter
    private String taskID;

    @Getter
    private ExecutorType taskType;

    @Getter
    @Setter
    private String inputData;

    @Getter
    @Setter
    private String outputData;

    @Getter
    @Setter
    private String status;

    public Task(String taskID, String taskType) {
        this.taskID = taskID;
        this.taskType = new ExecutorType(taskType);
    }

    public Task(String taskID, ExecutorType taskType) {
        this.taskID = taskID;
        this.taskType = taskType;
    }

    public Task(String taskID, ExecutorType taskType, String inputData) {
        this.taskID = taskID;
        this.taskType = taskType;
        this.inputData = inputData;
    }

    public Task() {}

}
