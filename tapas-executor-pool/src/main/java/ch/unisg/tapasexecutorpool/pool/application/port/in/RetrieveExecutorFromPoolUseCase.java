package ch.unisg.tapasexecutorpool.pool.application.port.in;

import ch.unisg.tapascommon.pool.adapter.in.formats.ExecutorJsonRepresentation;

import java.util.Optional;

public interface RetrieveExecutorFromPoolUseCase {
    Optional<ExecutorJsonRepresentation> retrieveExecutorFromPool(RetrieveExecutorFromPoolCommand command);
}
