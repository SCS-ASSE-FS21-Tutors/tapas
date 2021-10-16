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
    private final ListOfExecutors listOfExecutors;

    private static final ExecutorPool EXECUTOR_POOL = new ExecutorPool(new ExecutorPoolName("tapas-executor-pool-group05"));


    public ExecutorPool(ExecutorPoolName executorPoolName) {
        this.executorPoolName = executorPoolName;
        this.listOfExecutors = new ListOfExecutors(new LinkedList<Executor>());
    }


    public static ExecutorPool getTapasExecutorPool() {
        return EXECUTOR_POOL;
    }

    public Executor addNewExecutorWithUrlAndType(Executor.ExecutorUrl url, Executor.ExecutorType type) {
        Executor newExecutor = Executor.createExecutorWithUrlAndType(url, type);
        listOfExecutors.value.add(newExecutor);
        System.out.println("Number of executors: "+listOfExecutors.value.size());
        return newExecutor;
    }

    public Optional<Executor> retrieveExecutorById(Executor.ExecutorId id) {
        for (Executor executor : listOfExecutors.value) {
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
    public static class ListOfExecutors {
        private List<Executor> value;
    }

}
