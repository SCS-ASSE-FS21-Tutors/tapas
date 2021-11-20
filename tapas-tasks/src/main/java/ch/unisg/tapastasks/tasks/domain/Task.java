package ch.unisg.tapastasks.tasks.domain;

import lombok.*;

import java.util.UUID;

/**This is a domain entity**/
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    public enum Status {
        OPEN, ASSIGNED, RUNNING, EXECUTED
    }

    @Getter
    private TaskId taskId;

    @Getter
    private TaskName taskName;

    @Getter
    private TaskType taskType;

    @Getter @Setter
    private OriginalTaskUri originalTaskUri;

    @Getter @Setter
    private TaskStatus taskStatus;

    @Getter @Setter
    private ServiceProvider provider;

    @Getter @Setter
    private InputData inputData;

    @Getter @Setter
    private OutputData outputData;

    public Task(TaskName taskName, TaskType taskType) {
        this.taskId = new TaskId(UUID.randomUUID().toString());
        this.taskName = taskName;
        this.taskType = taskType;
        this.originalTaskUri = null;
        this.taskStatus = new TaskStatus(Status.OPEN);
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

    public Task(TaskId taskId, TaskName taskName, TaskType taskType) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskType = taskType;
        this.originalTaskUri = null;
        this.taskStatus = new TaskStatus(Status.OPEN);
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
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class TaskId {
        String value;
    }

    @Value
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class TaskName {
        String value;
    }

    @Value
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class TaskType {
        private String value;
    }

    @Value
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class OriginalTaskUri {
        String value;
    }

    @Value
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class TaskStatus {
        Status value;
    }

    @Value
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class ServiceProvider {
        String value;
    }

    @Value
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class InputData {
        String value;
    }

    @Value
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class OutputData {
        String value;
    }
    @Override
    public String toString() {
        return taskId.getValue() + " | "+ taskName.getValue() + " | " + taskType.getValue() +" | " + taskStatus.getValue().name() + " | " + ((outputData != null) ? outputData.getValue() : "");
    }
}
