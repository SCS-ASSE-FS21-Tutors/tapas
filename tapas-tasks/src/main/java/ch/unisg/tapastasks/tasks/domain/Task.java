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
    @Setter
    private TaskState taskState;

    public Task(TaskName taskName, TaskType taskType) {
        this.taskName = taskName;
        this.taskType = taskType;
        this.taskState = new TaskState(State.OPEN);
        this.taskId = new TaskId(UUID.randomUUID().toString());
    }

    protected static Task createTaskWithNameAndType(TaskName name, TaskType type) {
        //This is a simple debug message to see that the request has reached the right method in the core
        System.out.println("New Task: " + name.getValue() + " " + type.getValue());
        return new Task(name,type);
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
}
