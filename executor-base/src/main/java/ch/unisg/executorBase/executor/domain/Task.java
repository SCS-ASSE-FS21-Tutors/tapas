package ch.unisg.executorbase.executor.domain;

import lombok.Getter;
import lombok.Setter;

public class Task {

    @Getter
    private String taskID;

    @Getter
    @Setter
    private String result;

    public Task(String taskID) {
        this.taskID = taskID;
    }

}
