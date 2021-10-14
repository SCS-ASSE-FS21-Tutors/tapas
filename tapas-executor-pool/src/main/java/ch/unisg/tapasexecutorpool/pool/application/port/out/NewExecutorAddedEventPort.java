package ch.unisg.tapasexecutorpool.pool.application.port.out;

import ch.unisg.tapasexecutorpool.pool.domain.NewExecutorAddedEvent;

public interface NewExecutorAddedEventPort {

    void publishNewTaskAddedEvent(NewExecutorAddedEvent event);
}
