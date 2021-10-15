package ch.unisg.tapasexecutors.executors.domain;

import lombok.Getter;
import lombok.Value;


public class Task {

    @Getter
    private final TaskId taskId;

    @Getter
    private final TaskListName taskListName;

    public Task(TaskId taskId, TaskListName taskListName) {
        this.taskId = taskId;
        this.taskListName = taskListName;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskListName=" + taskListName +
                '}';
    }

    @Value
    public static class TaskId {
        String value;
    }

    @Value
    public static class TaskListName {
        String value;
    }
}
