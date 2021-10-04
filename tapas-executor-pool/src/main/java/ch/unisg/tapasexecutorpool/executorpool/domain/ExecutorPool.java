package ch.unisg.tapasexecutorpool.executorpool.domain;

import lombok.Getter;
import lombok.Value;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


/**This is our aggregate root**/
public class ExecutorPool {

    @Getter
    private final ExecutorPoolName executorPoolName;

    @Getter
    private final PoolOfExecutors poolOfExecutors;

    private static final ExecutorPool executorPool = new ExecutorPool(new ExecutorPoolName("tapas-executor-pool"));

    private ExecutorPool(ExecutorPoolName executorPoolName) {
        this.executorPoolName = executorPoolName;
        this.poolOfExecutors = new PoolOfExecutors(new LinkedList<Executor>());
    }

    public static ExecutorPool getTapasExecutorPool() {
        return executorPool;
    }

    //Only the aggregate root is allowed to create new tasks and add them to the task list.
    //Note: Here we could add some sophisticated invariants/business rules that the aggregate root checks
    public Executor addNewExecutorWithNameAndType(Executor.ExecutorName name, Executor.TaskType type) {
        Executor newExecutor = Executor.createTaskWithNameAndType(name,type);
        poolOfExecutors.value.add(newExecutor);
        //This is a simple debug message to see that the task list is growing with each new request
        System.out.println("Number of executors: "+ poolOfExecutors.value.size());
        //Here we would also publish a domain event to other entities in the core interested in this event.
        //However, we skip this here as it makes the core even more complex (e.g., we have to implement a light-weight
        //domain event publisher and subscribers (see "Implementing Domain-Driven Design by V. Vernon, pp. 296ff).
        return newExecutor;
    }

    public Optional<Executor> retrieveTaskById(Executor.ExecutorId id) {
        for (Executor executor : poolOfExecutors.value) {
            if (executor.getExecutorId().getValue().equalsIgnoreCase(id.getValue())) {
                return Optional.of(executor);
            }
        }

        return Optional.empty();
    }

    @Value
    public static class ExecutorPoolName {
        private String value;
    }

    @Value
    public static class PoolOfExecutors {
        private List<Executor> value;
    }

}
