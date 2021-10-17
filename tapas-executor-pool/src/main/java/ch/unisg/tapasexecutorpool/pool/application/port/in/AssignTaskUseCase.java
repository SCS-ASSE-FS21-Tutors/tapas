package ch.unisg.tapasexecutorpool.pool.application.port.in;

import ch.unisg.tapasexecutorpool.pool.domain.Executor;

public interface AssignTaskUseCase {
    Executor assignTask(AssignTaskCommand command);
}
