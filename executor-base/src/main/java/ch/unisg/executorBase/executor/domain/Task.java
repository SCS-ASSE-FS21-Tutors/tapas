package ch.unisg.executorbase.executor.domain;

import lombok.Getter;
import lombok.Setter;

public class Task {

    @Getter
    private String taskID;

    @Getter
    @Setter
    private String result;

    @Getter
    private String[] input;

    public Task(String taskID, String... input) {
        this.taskID = taskID;
        this.input = input;
    }

}
