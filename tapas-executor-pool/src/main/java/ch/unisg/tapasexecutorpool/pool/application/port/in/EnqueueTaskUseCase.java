package ch.unisg.tapasexecutorpool.pool.application.port.in;

import ch.unisg.tapasexecutorpool.pool.domain.Task;

public interface EnqueueTaskUseCase {

    void enqueueTask(Task task);
}
