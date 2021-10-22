package ch.unisg.tapasexecutorrobot.executor.application.port.out;

import ch.unisg.tapasexecutorrobot.executor.domain.ExecutorStateChangedEvent;

public interface ExecutorStateChangedEventPort {
    void publishExecutorStateChangedEvent(ExecutorStateChangedEvent event);
}
