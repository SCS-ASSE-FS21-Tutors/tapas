package ch.unisg.tapasexecutorcalc.executor.application.port.out;

import ch.unisg.tapasexecutorcalc.executor.domain.TaskExecutionCompletedEvent;

public interface TaskExecutionCompletedEventPort {
    void publishTaskExecutionCompletedEvent(TaskExecutionCompletedEvent event);
}
