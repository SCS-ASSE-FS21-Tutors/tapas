package ch.unisg.tapastasks.tasks.domain;

import lombok.*;

import java.util.UUID;

/**
 * This is a domain entity
 **/
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

    @Getter
    @Setter
    private OriginalTaskUri originalTaskUri;

    @Getter
    @Setter
    private TaskStatus taskStatus;

    @Getter
    @Setter
    private ServiceProvider provider;

    @Getter
    @Setter
    private InputData inputData;

    @Getter
    @Setter
    private OutputData outputData;

    public static Task createNewTaskWithRandomTaskId(TaskName taskName, TaskType taskType, InputData inputData) {
        return new Task(
            new TaskId(UUID.randomUUID().toString()),
            taskName,
            taskType,
            new OriginalTaskUri(),
            new TaskStatus(Status.OPEN),
            new ServiceProvider(),
            inputData,
            new OutputData()
        );
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
        return taskId.getValue() + " | " + taskName.getValue() + " | " + taskType.getValue() + " | " + taskStatus.getValue().name() + " | " + ((outputData != null) ? outputData.getValue() : "");
    }
}
