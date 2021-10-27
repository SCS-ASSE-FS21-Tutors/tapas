package ch.unisg.assignment.assignment.domain;

import ch.unisg.common.valueobject.ExecutorURI;
import lombok.Getter;

public class RosterItem {

    @Getter
    private String taskID;

    @Getter
    private String taskType;

    @Getter
    private ExecutorURI executorURI;

    public RosterItem(String taskID, String taskType, ExecutorURI executorURI) {
        this.taskID = taskID;
        this.taskType = taskType;
        this.executorURI = executorURI;
    }

}
