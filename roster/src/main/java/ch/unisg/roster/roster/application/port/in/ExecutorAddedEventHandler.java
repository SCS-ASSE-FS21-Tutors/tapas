package ch.unisg.roster.roster.application.port.in;

public interface ExecutorAddedEventHandler {

    boolean handleNewExecutorEvent(ExecutorAddedEvent executorAddedEvent);
}
