package ch.unisg.executorpool.application.port.in;

import ch.unisg.executorpool.domain.ExecutorClass;

import java.util.Optional;

public interface RemoveExecutorFromExecutorPoolUseCase {
    Optional<ExecutorClass> removeExecutorFromExecutorPool(RemoveExecutorFromExecutorPoolCommand command);
}
