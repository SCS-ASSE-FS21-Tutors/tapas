package ch.unisg.tapasexecutorrobot.executor.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

public class Task {

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

    @Getter
    @Setter
    private TaskPayload taskPayload;

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

    @Value
    public static class TaskPayload {
        private String value;
    }
}
