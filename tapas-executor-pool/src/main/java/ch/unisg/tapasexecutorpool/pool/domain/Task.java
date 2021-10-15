package ch.unisg.tapasexecutorpool.pool.domain;

import lombok.Getter;
import lombok.Value;


/**This is a domain entity**/
public class Task {

    @Getter
    private final TaskId taskId;

    @Getter
    private final TaskName taskName;

    @Getter
    private final TaskType taskType;

    public Task(TaskId taskId, TaskName taskName, TaskType taskType) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskType = taskType;
    }

    @Value
    public static class TaskId {
        private String value;
    }

    @Value
    public static class TaskName {
        private String value;
    }

    @Value
    public static class TaskType {
        private String value;
    }
}
