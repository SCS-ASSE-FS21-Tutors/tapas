package ch.unisg.tapasexecutorpool.pool.application.port.in;

import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.Task;

public interface NotifyTaskCompletionUseCase {
    void notifyTaskCompletion(NotifyTaskCompletionCommand command);
}
