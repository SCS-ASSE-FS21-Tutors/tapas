package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

public interface AddExecutorToPoolUseCase {
    // Add an executor to the existing pool and return whether the operation was successful
    boolean addExecutorToPool(AddExecutorToPoolCommand command);
}
