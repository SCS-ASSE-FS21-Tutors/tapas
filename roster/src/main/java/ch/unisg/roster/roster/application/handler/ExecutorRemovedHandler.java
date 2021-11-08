package ch.unisg.roster.roster.application.handler;

import ch.unisg.roster.roster.application.port.in.ExecutorRemovedEvent;
import ch.unisg.roster.roster.application.port.in.ExecutorRemovedEventHandler;
import ch.unisg.roster.roster.domain.ExecutorRegistry;
import org.springframework.stereotype.Component;

/**
 * Handler for executor removed events. It removes the executor from this roster's executor
 * registry.
 */
@Component
public class ExecutorRemovedHandler implements ExecutorRemovedEventHandler {

    @Override
    public boolean handleExecutorRemovedEvent(ExecutorRemovedEvent executorRemovedEvent) {
        return ExecutorRegistry.getInstance().removeExecutor(executorRemovedEvent.getExecutorURI());
    }
}
