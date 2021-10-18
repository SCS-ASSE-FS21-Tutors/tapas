package ch.unisg.tapastasks.tasks.domain;

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
    private final TaskId taskId;

    @Getter
    private final TaskName taskName;

    @Getter
    private final TaskType taskType;

    @Getter
    private TaskState taskState;

    @Getter
    private TaskPayload taskPayload;

    /*public Task(TaskName taskName, TaskType taskType) {
        this.taskName = taskName;
        this.taskType = taskType;
        this.taskState = new TaskState(State.OPEN);
        this.taskId = new TaskId(UUID.randomUUID().toString());
        this.taskPayload = new TaskPayload("");
    }*/

    public Task(TaskName taskName, TaskType taskType, TaskPayload taskPayload) {
        this.taskName = taskName;
        this.taskType = taskType;
        this.taskState = new TaskState(State.OPEN);
        this.taskId = new TaskId(UUID.randomUUID().toString());
        this.taskPayload = taskPayload;
    }

    /*protected static Task createTaskWithNameAndType(TaskName name, TaskType type) {
        System.out.println("New Task: " + name.getValue() + " " + type.getValue());
        return new Task(name, type);
    }*/

    protected static Task createTaskWithPayload(TaskName name, TaskType type, TaskPayload payload) {
        System.out.println("New Task: " + name.getValue() + " " + type.getValue());
        return new Task(name, type, payload);
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
    public static class TaskState {
        private State value;
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
