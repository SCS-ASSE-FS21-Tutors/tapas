package ch.unisg.tapas.auctionhouse.application.handler;

import ch.unisg.tapas.auctionhouse.application.port.in.ExecutorAddedEvent;
import ch.unisg.tapas.auctionhouse.application.port.in.ExecutorAddedEventHandler;
import ch.unisg.tapas.auctionhouse.domain.ExecutorRegistry;
import org.springframework.stereotype.Component;

@Component
public class ExecutorAddedHandler implements ExecutorAddedEventHandler {

    @Override
    public boolean handleNewExecutorEvent(ExecutorAddedEvent executorAddedEvent) {
        return ExecutorRegistry.getInstance().addExecutor(executorAddedEvent.getTaskType(),
            executorAddedEvent.getExecutorId());
    }
}
