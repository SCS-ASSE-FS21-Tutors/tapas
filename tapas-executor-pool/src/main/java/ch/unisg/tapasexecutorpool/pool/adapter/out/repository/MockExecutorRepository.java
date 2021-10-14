package ch.unisg.tapasexecutorpool.pool.adapter.out.repository;

import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Simple mock implementation of a repository with no DB backend.
 * Just stores the executors in a in-memory collection.
 */
@Component
public class MockExecutorRepository implements ExecutorRepository {

    private HashSet<Executor> inMemorySet;

    public MockExecutorRepository() {
        this.inMemorySet = new HashSet<>();
    }

    @Override
    public Collection<Executor> getExecutors() {

        return Collections.unmodifiableCollection(inMemorySet);
    }

    @Override
    public void addExecutor(Executor executor) {
        inMemorySet.add(executor);
    }
}
