package ch.unisg.executorpool.application.port.out;

import ch.unisg.executorpool.domain.ExecutorRemovedEvent;

public interface ExecutorRemovedEventPort {
    void publishExecutorRemovedEvent(ExecutorRemovedEvent event);
}
