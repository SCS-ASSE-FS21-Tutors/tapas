package ch.unisg.tapasexecutorpool.executorpool.domain;

import lombok.Getter;
import lombok.Value;

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
    private final TaskListName taskListName;

    public Task(TaskName taskName, TaskType taskType, TaskId taskId, TaskListName taskListName, String taskState) {
        this.taskName = taskName;
        this.taskType = taskType;
        this.taskId = taskId;
        this.taskListName = taskListName;
        this.taskState = new TaskState(State.valueOf(taskState));
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskName=" + taskName +
                ", taskType=" + taskType +
                ", taskState=" + taskState +
                ", taskListName=" + taskListName +
                '}';
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
    public static class TaskState {
        State value;
    }

    @Value
    public static class TaskType {
        String value;
    }

    @Value
    public static class TaskListName {
        String value;
    }
}
