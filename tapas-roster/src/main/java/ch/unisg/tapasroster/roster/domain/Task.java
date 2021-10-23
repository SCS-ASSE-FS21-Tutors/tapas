package ch.unisg.tapasroster.roster.domain;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

/**This is a domain entity**/
public class Task {

    @Getter
    private final TaskId taskId;

    @Getter
    private final TaskUri taskUri;

    @Getter
    private final TaskType taskType;


    public Task(TaskId taskId, TaskType taskType, TaskUri taskUri) {
        this.taskType = taskType;
        this.taskId = taskId;
        this.taskUri = taskUri;
    }


    @Value
    public static class TaskId {
        private String value;
    }

    @Value
    public static class TaskUri {
        private String value;
    }


    @Value
    public static class TaskType {
        private String value;
    }
}