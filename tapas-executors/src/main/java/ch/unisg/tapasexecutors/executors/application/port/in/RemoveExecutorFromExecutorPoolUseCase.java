package ch.unisg.tapasexecutors.executors.application.port.in;

import ch.unisg.tapasexecutors.executors.domain.Executors;

public interface RemoveExecutorFromExecutorPoolUseCase {
    Executors removeExecutorFromExecutorPoolUseCase(RemoveExecutorFromExecutorPoolUseCase command);
}
