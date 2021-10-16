package ch.unisg.assignment.assignment.domain;

import lombok.Getter;
import lombok.Setter;

public class Task {

    @Getter
    private String taskID;

    @Getter
    private String taskType;

    @Getter
    @Setter
    private String result;

    @Getter
    @Setter
    private String status;

    public Task(String taskID, String taskType) {
        this.taskID = taskID;
        this.taskType = taskType;
    }

}
