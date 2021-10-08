package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

public interface RemoveExecutorFromPoolByIDUseCase {
    // Remove Executor from pool and return whether operation was successful
    boolean removeExecutorFromPoolByID(ExecutorByIDCommand command);
}
