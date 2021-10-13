package ch.unisg.tapasexecutorrobot.executor.application.port.in;

import ch.unisg.tapasexecutorrobot.executor.domain.Task;

public interface ExecuteTaskUseCase {
    Task executeTask(ExecuteTaskCommand command);
}
