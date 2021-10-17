package ch.unisg.assignment.assignment.domain;

import ch.unisg.assignment.assignment.domain.valueobject.ExecutorType;
import lombok.Getter;
import lombok.Setter;

public class Task {

    @Getter
    private String taskID;

    @Getter
    private ExecutorType taskType;

    @Getter
    @Setter
    private String result;

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

    public Task() {};

}
