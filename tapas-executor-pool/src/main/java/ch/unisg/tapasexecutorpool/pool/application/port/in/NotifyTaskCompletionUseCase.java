package ch.unisg.tapasexecutorpool.pool.application.port.in;

public interface NotifyTaskCompletionUseCase {
    void notifyTaskCompletion(NotifyTaskCompletionCommand command);
}
