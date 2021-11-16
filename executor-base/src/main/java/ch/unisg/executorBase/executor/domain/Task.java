package ch.unisg.executorbase.executor.domain;

import lombok.Getter;
import lombok.Setter;

public class Task {

    @Getter
    private String taskID;

    @Getter
    @Setter
    private String outputData;

    // TODO maybe create a value object for inputData so we can make sure it is in the right
    // format.
    @Getter
    private String inputData;

    public Task(String taskID, String inputData) {
        this.taskID = taskID;
        this.inputData= inputData;
    }

}
