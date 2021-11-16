package ch.unisg.tapasexecutorpool.pool.adapter.out.repository;

import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

/**
 * Simple mock implementation of a repository with no DB backend.
 * Just stores the executors in a in-memory collection.
 */
@Component
public class InternalExecutorRepository implements ExecutorRepository {

    private HashSet<Executor> inMemorySet;

    public InternalExecutorRepository() {
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

    @Override
    public Optional<Executor> findByTaskId(String taskId) {

        return inMemorySet.stream()
                .filter(e -> e.getAssignedTask() != null)
                .filter(e -> e.getAssignedTask().getTaskId().getValue().equals(taskId))
                .findFirst();
    }

    @Override
    public void updateExecutor(Executor executor) {

        Optional<Executor> exOptional = inMemorySet.stream().filter(e -> e.getExecutorId().equals(executor.getExecutorId())).findFirst();

        if(exOptional.isEmpty()){
            throw new IllegalArgumentException("No executor with id=" + executor.getExecutorId() + " found");
        }
        else {
            Executor ex = exOptional.get();
            ex.setAssignedTask(executor.getAssignedTask());
            ex.setExecutorState(executor.getExecutorState());
        }
    }

    /**
     * Should only used for testing
     */
    public void deleteAll(){

        inMemorySet.clear();
    }
}
