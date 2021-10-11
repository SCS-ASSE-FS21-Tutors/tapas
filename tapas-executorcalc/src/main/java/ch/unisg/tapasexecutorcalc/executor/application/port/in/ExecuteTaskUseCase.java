package ch.unisg.tapasexecutorcalc.executor.application.port.in;

import ch.unisg.tapasexecutorcalc.executor.domain.Task;

public interface ExecuteTaskUseCase {
    Task executeTask(ExecuteTaskCommand command);
}
