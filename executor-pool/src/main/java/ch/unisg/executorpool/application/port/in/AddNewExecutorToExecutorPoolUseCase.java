package ch.unisg.executorpool.application.port.in;

import ch.unisg.executorpool.domain.ExecutorClass;

public interface AddNewExecutorToExecutorPoolUseCase {
    ExecutorClass addNewExecutorToExecutorPool(AddNewExecutorToExecutorPoolCommand command);
}
