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
    private final OriginalTaskUri originalTaskUri;

    @Getter @Setter
    private TaskStatus taskStatus;

    @Getter @Setter
    private ServiceProvider provider;

    @Getter @Setter
    private Input input;

    @Getter @Setter
    private Output output;

    public Task(TaskName taskName, TaskType taskType, OriginalTaskUri taskUri) {
        this.taskId = new TaskId(UUID.randomUUID().toString());

        this.taskName = taskName;
        this.taskType = taskType;
        this.originalTaskUri = taskUri;

        this.taskStatus = new TaskStatus(State.OPEN);

        this.input = null;
        this.output = null;
    }

    protected static Task createTaskWithNameAndType(TaskName name, TaskType type) {
        //This is a simple debug message to see that the request has reached the right method in the core
        System.out.println("New Task: " + name.getValue() + " " + type.getValue());
        return new Task(name, type, null);
    }

    protected static Task createTaskWithNameAndTypeAndOriginalTaskUri(TaskName name, TaskType type,
            OriginalTaskUri originalTaskUri) {
        return new Task(name, type, originalTaskUri);
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

    @Value
    public static class OriginalTaskUri {
        private String value;
    }

    @Value
    public static class TaskStatus {
        private State value;
    }

    @Value
    public static class ServiceProvider {
        private String value;
    }

    @Value
    public static class Input {
        private String value;
    }

    @Value
    public static class Output {
        private String value;
    }
}
