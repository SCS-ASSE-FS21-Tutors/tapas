package ch.unisg.executor1.executor.domain;

import lombok.Data;
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
