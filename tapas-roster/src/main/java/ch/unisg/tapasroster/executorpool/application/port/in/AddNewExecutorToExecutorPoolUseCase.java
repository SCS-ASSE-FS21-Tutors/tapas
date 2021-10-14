package ch.unisg.tapasroster.executorpool.application.port.in;


import ch.unisg.tapasroster.executorpool.domain.Executor;

public interface AddNewExecutorToExecutorPoolUseCase {
    Executor addNewExecutorToExecutorPool(AddNewExecutorToExecutorPoolCommand command);
}
