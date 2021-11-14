package ch.unisg.executorpool.application.port.in;

import ch.unisg.executorpool.domain.ExecutorClass;

import java.util.List;

public interface GetAllExecutorsInExecutorPoolByTypeUseCase {
    List<ExecutorClass> getAllExecutorsInExecutorPoolByType(GetAllExecutorsInExecutorPoolByTypeQuery query);
}
