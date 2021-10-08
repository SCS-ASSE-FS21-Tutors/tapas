package ch.unisg.tapasexecutordigital.executordigital.application.port.out;

import ch.unisg.tapasexecutordigital.executordigital.domain.ExecutorStateChangedEvent;

public interface ExecutorStateChangedPort {
    void publishExecutorStateChanged(ExecutorStateChangedEvent event);
}
