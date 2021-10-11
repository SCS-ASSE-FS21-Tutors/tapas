package ch.unisg.executor1.executor.application.port.out;

import ch.unisg.executor1.executor.domain.ExecutionFinishedEvent;

public interface ExecutionFinishedEventPort {
    void publishExecutionFinishedEvent(ExecutionFinishedEvent event);
}
