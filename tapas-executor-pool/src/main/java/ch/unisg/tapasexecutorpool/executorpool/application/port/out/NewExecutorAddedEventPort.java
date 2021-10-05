package ch.unisg.tapasexecutorpool.executorpool.application.port.out;

import ch.unisg.tapasexecutorpool.executorpool.domain.NewExecutorAddedEvent;

public interface NewExecutorAddedEventPort {

    void publishNewTaskAddedEvent(NewExecutorAddedEvent event);
}
