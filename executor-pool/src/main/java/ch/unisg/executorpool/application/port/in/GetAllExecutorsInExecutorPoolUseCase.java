package ch.unisg.executorpool.application.port.in;

import ch.unisg.executorpool.domain.ExecutorClass;
import ch.unisg.executorpool.domain.ExecutorPool;

import java.util.List;

public interface GetAllExecutorsInExecutorPoolUseCase {
    List<ExecutorClass> getAllExecutorsInExecutorPool();
}
