package ch.unisg.tapasexecutorrobot.executor.application.port.in;

import ch.unisg.tapascommon.tasks.domain.Task;

public interface ExecuteTaskUseCase {
    Task executeTask(ExecuteTaskCommand command);
}
