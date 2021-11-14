package ch.unisg.executorpool.domain;

import ch.unisg.executorpool.domain.ExecutorClass;
import lombok.Getter;

public class ExecutorRemovedEvent {
    @Getter
    private ExecutorClass executorClass;

    public ExecutorRemovedEvent(ExecutorClass executorClass) { this.executorClass = executorClass; }
}
