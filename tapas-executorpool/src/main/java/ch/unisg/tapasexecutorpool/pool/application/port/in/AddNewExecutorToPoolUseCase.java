package ch.unisg.tapasexecutorpool.pool.application.port.in;

import ch.unisg.tapasexecutorpool.pool.domain.Executor;

public interface AddNewExecutorToPoolUseCase {
    Executor addNewExecutorToPool(AddNewExecutorToPoolCommand command);
}
