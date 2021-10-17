package ch.unisg.tapasexecutorpool.pool.application.port.repository;

import ch.unisg.tapasexecutorpool.pool.domain.Executor;

import java.util.Collection;

public interface ExecutorRepository {

    Collection<Executor> getExecutors();
    void addExecutor(Executor executor);
}
