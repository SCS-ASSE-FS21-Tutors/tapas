package ch.unisg.tapas.auctionhouse.application.port.in;

public interface ExecutorRemovedEventHandler {

    boolean handleExecutorRemovedEvent(ExecutorRemovedEvent executorRemovedEvent);
}
