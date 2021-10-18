package ch.unisg.tapas.auctionhouse.application.port.in;

public interface ExecutorAddedEventHandler {

    boolean handleNewExecutorEvent(ExecutorAddedEvent executorAddedEvent);
}
