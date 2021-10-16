package ch.unisg.assignment.assignment.domain;

import lombok.Getter;

public class RosterItem {

    @Getter
    private String taskID;

    @Getter
    private String taskType;

    @Getter
    private String executorIP;

    @Getter
    private int executorPort;


    public RosterItem(String taskID, String taskType, String executorIP, int executorPort) {
        this.taskID = taskID;
        this.taskType = taskType;
        this.executorIP = executorIP;
        this.executorPort = executorPort;
    }

}
