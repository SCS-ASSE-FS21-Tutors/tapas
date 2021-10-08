package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;

public interface GetExecutorByIDUseCase {
    // Get Executor by ID and return Executor
    Executor getExecutorByID(ExecutorByIDCommand command);
}
