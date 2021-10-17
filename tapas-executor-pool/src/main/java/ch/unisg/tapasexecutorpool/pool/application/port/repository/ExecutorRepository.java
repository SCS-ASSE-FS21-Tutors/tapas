package ch.unisg.tapasexecutorpool.pool.application.port.repository;

import ch.unisg.tapasexecutorpool.pool.domain.Executor;

import java.util.Collection;
import java.util.Optional;

public interface ExecutorRepository {

    Collection<Executor> getExecutors();
    void addExecutor(Executor executor);
    Optional<Executor> findByTaskId(String taskId);
    void updateExecutor(Executor assignedExecutor);
}
