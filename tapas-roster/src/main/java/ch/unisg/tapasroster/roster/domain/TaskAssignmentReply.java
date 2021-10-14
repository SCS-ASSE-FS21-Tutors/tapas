package ch.unisg.tapasroster.roster.domain;


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

    @Override
    public String toString() {
        return "TaskAssignmentReply{" +
                "executorName='" + executorName + '\'' +
                ", assignmentType='" + assignmentType + '\'' +
                '}';
    }
}
