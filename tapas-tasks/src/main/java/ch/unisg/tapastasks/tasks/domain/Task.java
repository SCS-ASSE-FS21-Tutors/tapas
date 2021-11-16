package ch.unisg.tapastasks.tasks.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.UUID;

/**This is a domain entity**/
public class Task {
    public enum Status {
        OPEN, ASSIGNED, RUNNING, EXECUTED
    }

    @Getter
    private final TaskId taskId;

    @Getter
    private final TaskName taskName;

    @Getter
    private final TaskType taskType;

    @Getter @Setter
    public TaskStatus taskStatus; // had to make public for CompleteTaskService

    @Getter
    public TaskResult taskResult; // same as above

    @Getter
    private final OriginalTaskUri originalTaskUri;

    @Getter @Setter
    private ServiceProvider provider;

    @Getter @Setter
    private InputData inputData;

    @Getter @Setter
    private OutputData outputData;

    public Task(TaskName taskName, TaskType taskType, OriginalTaskUri taskUri) {
        this.taskName = taskName;
        this.taskType = taskType;
        this.taskStatus = new TaskStatus(Status.OPEN);
        this.taskId = new TaskId(UUID.randomUUID().toString());
        this.taskResult = new TaskResult("");
        this.originalTaskUri = taskUri;

        this.inputData = null;
        this.outputData = null;
    }

    public Task(TaskName taskName, TaskType taskType, OriginalTaskUri taskUri, InputData inputData) {
        this.taskName = taskName;
        this.taskType = taskType;
        this.taskStatus = new TaskStatus(Status.OPEN);
        this.taskId = new TaskId(UUID.randomUUID().toString());
        this.taskResult = new TaskResult("");
        this.originalTaskUri = taskUri;

        this.inputData = inputData;
        this.outputData = null;
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

    protected static Task createTaskWithNameAndTypeAndOriginalTaskUriAndInputData(TaskName name, TaskType type,
    OriginalTaskUri originalTaskUri, InputData inputData) {
        return new Task(name, type, originalTaskUri, inputData);
    }

    @Value
    public static class TaskId {
        String value;
    }

    @Value
    public static class TaskName {
        String value;
    }

    @Value
    public static class TaskType {
        String value;
    }

    @Value
    public static class OriginalTaskUri {
        String value;
    }

    @Value
    public static class TaskStatus {
        Status value;
    }

    @Value
    public static class ServiceProvider {
        String value;
    }

    @Value
    public static class InputData {
        String value;
    }

    @Value
    public static class OutputData {
        String value;
    }

    @Value
    public static class TaskResult{
        private String value;
    }
}
