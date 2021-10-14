package ch.unisg.tapasroster.roster.application.port.in;


import ch.unisg.tapasroster.roster.domain.Executor;

public interface AddNewExecutorToExecutorPoolUseCase {
    Executor addNewExecutorToExecutorPool(AddNewExecutorToExecutorPoolCommand command);
}
