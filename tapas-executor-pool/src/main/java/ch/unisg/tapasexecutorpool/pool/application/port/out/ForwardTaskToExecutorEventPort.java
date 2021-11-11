package ch.unisg.tapasexecutorpool.pool.application.port.out;

import ch.unisg.tapasexecutorpool.pool.domain.ForwardTaskToExecutorEvent;

public interface ForwardTaskToExecutorEventPort {
    void forwardTaskToExecutorEvent(ForwardTaskToExecutorEvent event);
}
