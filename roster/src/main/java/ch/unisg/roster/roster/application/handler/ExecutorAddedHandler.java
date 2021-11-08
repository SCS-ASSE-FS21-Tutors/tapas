package ch.unisg.roster.roster.application.handler;

import ch.unisg.roster.roster.application.port.in.ExecutorAddedEvent;
import ch.unisg.roster.roster.application.port.in.ExecutorAddedEventHandler;
import ch.unisg.roster.roster.domain.ExecutorRegistry;
import org.springframework.stereotype.Component;

@Component
public class ExecutorAddedHandler implements ExecutorAddedEventHandler {

    @Override
    public boolean handleNewExecutorEvent(ExecutorAddedEvent executorAddedEvent) {
        return ExecutorRegistry.getInstance().addExecutor(executorAddedEvent.getExecutorType(),
            executorAddedEvent.getExecutorURI());
    }
}
