package ch.unisg.tapasexecutorpool.pool.application.port.in;

import ch.unisg.tapasexecutorpool.pool.adapter.in.formats.ExecutorJsonRepresentation;

public interface AddNewExecutorToPoolUseCase {
    ExecutorJsonRepresentation addNewExecutorToPool(AddNewExecutorToPoolCommand command);
}
