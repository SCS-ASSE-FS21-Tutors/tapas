package ch.unisg.tapasexecutorpool.executorpool.domain;


import lombok.Getter;

public class TaskAssignmentReply {

    @Getter
    public String executorName;

    @Getter
    public String assignmentType;

    public TaskAssignmentReply(String executorName, String assignmentType) {
        this.executorName = executorName;
        this.assignmentType = assignmentType;
    }
}
