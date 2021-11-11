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

    @Getter
    private final OriginalTaskUri originalTaskUri;

    @Getter @Setter
    private TaskStatus taskStatus;

    @Getter @Setter
    private ServiceProvider provider;

    @Getter @Setter
    private InputData inputData;

    @Getter @Setter
    private OutputData outputData;

    public Task(TaskName taskName, TaskType taskType, OriginalTaskUri taskUri) {
        this.taskId = new TaskId(UUID.randomUUID().toString());

        this.taskName = taskName;
        this.taskType = taskType;
        this.originalTaskUri = taskUri;

        this.taskStatus = new TaskStatus(Status.OPEN);

        this.inputData = null;
        this.outputData = null;
    }

    //Constructor from repo
    public Task(TaskId taskId, TaskName taskName, TaskType taskType, OriginalTaskUri taskUri,
                TaskStatus taskStatus) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskType = taskType;
        this.originalTaskUri = taskUri;
        this.taskStatus = taskStatus;
        this.inputData = null;
        this.outputData = null;
    }


    public static Task createTaskWithNameAndType(TaskName name, TaskType type) {
        //This is a simple debug message to see that the request has reached the right method in the core
        System.out.println("New Task: " + name.getValue() + " " + type.getValue());
        return new Task(name, type, null);
    }

    public static Task createTaskWithNameAndTypeAndOriginalTaskUri(TaskName name, TaskType type,
            OriginalTaskUri originalTaskUri) {
        return new Task(name, type, originalTaskUri);
    }

    //This is for recreating a task from a repository.
    public static Task withIdNameTypeOriginaluriStatus(TaskId taskId, TaskName taskName,
                                                               TaskType taskType,
                                                               OriginalTaskUri originalTaskUri,
                                                               TaskStatus taskStatus) {
        return new Task(taskId, taskName, taskType, originalTaskUri, taskStatus);
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
}
