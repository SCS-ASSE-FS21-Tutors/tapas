package ch.unisg.tapasexecutors.executors.application.port.out;

import ch.unisg.tapasexecutors.executors.domain.NewExecutorAddedEvent;

public interface NewExecutorAddedEventPort {

    void publishNewExecutorAddedEvent(NewExecutorAddedEvent event);
}
