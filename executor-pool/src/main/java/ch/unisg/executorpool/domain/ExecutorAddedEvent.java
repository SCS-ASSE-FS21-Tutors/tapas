package ch.unisg.executorpool.domain;

import lombok.Getter;

public class ExecutorAddedEvent {
    @Getter
    private ExecutorClass executorClass;

    public ExecutorAddedEvent(ExecutorClass executorClass) { this.executorClass = executorClass; }
}
