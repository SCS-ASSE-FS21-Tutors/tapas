package ch.unisg.tapasexecutors.executors.domain;

import lombok.Getter;

public class TaskFinishedEvent {

    @Getter
    private final Task task;

    public TaskFinishedEvent(Task task){
        this.task = task;
    }
}
