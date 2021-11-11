package ch.unisg.tapasexecutorpool.pool.application.port.in;

import ch.unisg.tapasexecutorpool.pool.domain.Executor;

import java.util.Optional;

public interface RetrieveExecutorFromPoolUseCase {
    Optional<Executor> retrieveExecutorFromPool(RetrieveExecutorFromPoolCommand command);
}
