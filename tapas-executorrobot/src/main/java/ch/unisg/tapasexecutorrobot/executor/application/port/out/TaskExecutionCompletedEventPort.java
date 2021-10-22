package ch.unisg.tapasexecutorrobot.executor.application.port.out;

import ch.unisg.tapasexecutorrobot.executor.domain.TaskExecutionCompletedEvent;

public interface TaskExecutionCompletedEventPort {
    void publishTaskExecutionCompletedEvent(TaskExecutionCompletedEvent event);
}
