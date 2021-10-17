package ch.unisg.executorBase.executor.application.port.out;

import ch.unisg.executorBase.executor.domain.ExecutionFinishedEvent;

public interface ExecutionFinishedEventPort {
    void publishExecutionFinishedEvent(ExecutionFinishedEvent event);
}
