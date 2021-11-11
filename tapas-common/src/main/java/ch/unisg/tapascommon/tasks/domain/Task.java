package ch.unisg.tapascommon.tasks.domain;

import ch.unisg.tapascommon.ServiceHostAddresses;
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
    private OriginalTaskUri originalTaskUri;

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

    public static Task createNewTask(TaskName taskName, TaskType taskType, OriginalTaskUri taskUri) {
        var task = new Task(
                new TaskId(UUID.randomUUID().toString()),
                taskName,
                taskType,
                taskUri,
                new TaskStatus(Status.OPEN),
                null,
                null,
                null
        );

        if (taskUri == null) {
            task.originalTaskUri = new OriginalTaskUri(ServiceHostAddresses.getTaskServiceHostAddress() + "/tasks/" + task.taskId.getValue());
        }

        return task;
    }

    public static Task createNewTask(TaskName taskName, TaskType taskType) {
        return createNewTask(taskName, taskType, null);
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
}
