package ch.unisg.tapasexecutorcalc.executor.application.port.out;

import ch.unisg.tapasexecutorcalc.executor.domain.ExecutorStateChangedEvent;

public interface ExecutorStateChangedEventPort {
    void publishExecutorStateChangedEvent(ExecutorStateChangedEvent event);
}
