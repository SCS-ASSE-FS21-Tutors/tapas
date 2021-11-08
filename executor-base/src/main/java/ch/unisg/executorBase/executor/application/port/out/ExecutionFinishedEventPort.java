package ch.unisg.executorbase.executor.application.port.out;

import ch.unisg.executorbase.executor.domain.ExecutionFinishedEvent;

public interface ExecutionFinishedEventPort {
    void publishExecutionFinishedEvent(ExecutionFinishedEvent event);
}
