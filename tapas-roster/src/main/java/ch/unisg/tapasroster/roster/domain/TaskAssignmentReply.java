package ch.unisg.tapasroster.roster.domain;


import ch.unisg.tapasroster.executorpool.domain.Executor;
import lombok.Getter;

public class TaskAssignmentReply {

    @Getter
    private final Executor executor;

    @Getter
    private final Task task;

    @Getter
    private final String assignmentType;

    public TaskAssignmentReply(Executor executor, Task task, String assignmentType) {
        this.executor = executor;
        this.task = task;
        this.assignmentType = assignmentType;
    }

    public String getExecutorName() {
        return this.executor.getExecutorName().getValue();
    }

    @Override
    public String toString() {
        return "TaskAssignmentReply{" +
                "executorName=" + executor.getExecutorName().getValue() +
                ", taskName=" + task.getTaskName().getValue() +
                ", assignmentType='" + assignmentType + '\'' +
                '}';
    }
}
