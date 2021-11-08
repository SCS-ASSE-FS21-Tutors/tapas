package ch.unisg.roster.roster.application.port.in;

public interface ExecutorRemovedEventHandler {

    boolean handleExecutorRemovedEvent(ExecutorRemovedEvent executorRemovedEvent);
}
