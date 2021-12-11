package ch.unisg.tapastasks.tasks.domain;

import lombok.Value;

@Value
public class NewTaskAddedEvent {
    public Task task;
    public String taskListName;

    public NewTaskAddedEvent(Task task, String taskListName) {
        this.task = task;
        this.taskListName = taskListName;
    }
}
