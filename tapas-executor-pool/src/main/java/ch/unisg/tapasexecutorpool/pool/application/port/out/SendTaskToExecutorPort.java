package ch.unisg.tapasexecutorpool.pool.application.port.out;

import ch.unisg.tapasexecutorpool.pool.application.port.in.AssignTaskCommand;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.Task;

public interface SendTaskToExecutorPort {

    void sendTaskToExecutor(Task.TaskId taskId, Executor executor);
}
