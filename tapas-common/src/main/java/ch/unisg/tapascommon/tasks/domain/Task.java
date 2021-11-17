package ch.unisg.tapascommon.tasks.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.UUID;

public class Task {
    public enum Status {
        OPEN, ASSIGNED, RUNNING, EXECUTED
    }

    public enum Type {
        BIGROBOT, SMALLROBOT, COMPUTATION, RANDOMTEXT
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

    public Task(
            TaskId taskId,
            TaskName taskName,
            TaskType taskType,
            OriginalTaskUri taskUri,
            TaskStatus taskStatus,
            ServiceProvider provider,
            InputData inputData,
            OutputData outputData
    ) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskType = taskType;
        this.originalTaskUri = taskUri;
        this.taskStatus = taskStatus;
        this.provider = provider;
        this.inputData = inputData;
        this.outputData = outputData;
    }

    public static Task createNewTask(TaskName taskName, TaskType taskType, OriginalTaskUri originalTaskUri) {
        return new Task(
                new TaskId(UUID.randomUUID().toString()),
                taskName,
                taskType,
                originalTaskUri,
                new TaskStatus(Status.OPEN),
                null,
                null,
                null
        );
    }

    public static Task createNewTask(TaskName taskName, TaskType taskType) {
        return createNewTask(taskName, taskType, null);
    }

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
        Type value;
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

    @Override
    public String toString() {
        var originalTaskUriString = originalTaskUri != null ? originalTaskUri.getValue() : "";
        var providerString = provider != null ? provider.getValue() : "";
        var inputDataString = inputData !=  null ? inputData.getValue() : "";
        var outputDataString = outputData != null ? outputData.getValue() : "";

        return "Task{" +
                "taskId=" + taskId.getValue() +
                ", taskName=" + taskName.getValue() +
                ", taskType=" + taskType.getValue().name() +
                ", originalTaskUri=" + originalTaskUriString +
                ", taskStatus=" + taskStatus.getValue().name() +
                ", provider=" + providerString +
                ", inputData=" + inputDataString +
                ", outputData=" + outputDataString +
                '}';
    }
}
