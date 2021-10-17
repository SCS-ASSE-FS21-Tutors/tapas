package ch.unisg.tapasexecutorpool.pool.application.port.in;

import ch.unisg.tapasexecutorpool.pool.domain.Executor;

public interface AddNewExecutorToExecutorPoolUseCase {
    Executor addNewExecutorToExecutorPool(AddNewExecutorToExecutorPoolCommand command);
}
