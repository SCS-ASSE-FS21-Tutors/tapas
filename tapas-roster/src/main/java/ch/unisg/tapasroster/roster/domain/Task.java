package ch.unisg.tapasroster.roster.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.UUID;

/**This is a domain entity**/
public class Task {
    public enum State {
        OPEN, ASSIGNED, RUNNING, EXECUTED
    }

    @Getter
    @Setter
    private TaskId taskId;

    @Getter
    @Setter
    private TaskName taskName;

    @Getter
    @Setter
    private TaskType taskType;

    @Getter
    @Setter
    private TaskState taskState;

    public Task() { }

    @Value
    public static class TaskId {
        private String value;
    }

    @Value
    public static class TaskName {
        private String value;
    }

    @Value
    public static class TaskState {
        private String value;
    }

    @Value
    public static class TaskType {
        private String value;
    }
}
