package ch.unisg.tapastasks.tasks.application.port.out;

public interface ExternalTaskExecutedEventHandler {
    void handleEvent(ExternalTaskExecutedEvent externalTaskExecutedEvent);
}
