package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;

public interface AddExecutorToPoolUseCase {
    Executor addNewExecutorToExecutorPool(AddExecutorToPoolCommand command);
}