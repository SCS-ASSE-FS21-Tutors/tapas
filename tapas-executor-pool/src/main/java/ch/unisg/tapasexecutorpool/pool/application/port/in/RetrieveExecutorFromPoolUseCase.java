package ch.unisg.tapasexecutorpool.pool.application.port.in;

import ch.unisg.tapasexecutorpool.pool.adapter.in.formats.ExecutorJsonRepresentation;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;

import java.util.Optional;

public interface RetrieveExecutorFromPoolUseCase {
    Optional<ExecutorJsonRepresentation> retrieveExecutorFromPool(RetrieveExecutorFromPoolCommand command);
}
