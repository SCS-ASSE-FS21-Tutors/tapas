package ch.unisg.assignment.assignment.domain;

import ch.unisg.assignment.assignment.domain.valueobject.IP4Adress;
import ch.unisg.assignment.assignment.domain.valueobject.Port;
import lombok.Getter;

public class RosterItem {

    @Getter
    private String taskID;

    @Getter
    private String taskType;

    @Getter
    private IP4Adress executorIP;

    @Getter
    private Port executorPort;


    public RosterItem(String taskID, String taskType, IP4Adress executorIP, Port executorPort) {
        this.taskID = taskID;
        this.taskType = taskType;
        this.executorIP = executorIP;
        this.executorPort = executorPort;
    }

}
