package ch.unisg.tapasexecutorpool.pool.domain;

import lombok.Getter;
import lombok.Value;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ExecutorPool {

    @Getter
    private final ExecutorPoolName executorPoolName;

    @Getter
    private final ListOfExecutors listOfExecutors;

    private static final ExecutorPool EXECUTOR_POOL = new ExecutorPool(new ExecutorPoolName("tapas-executorpool"));

    private ExecutorPool(ExecutorPoolName executorPoolName) {
        this.executorPoolName = executorPoolName;
        this.listOfExecutors = new ListOfExecutors(new LinkedList<>());
    }

    public static ExecutorPool getTapasExecutorPool() {
        return EXECUTOR_POOL;
    }

    public Executor addNewExecutorWithNameAndType(Executor.ExecutorName name, Executor.ExecutorType type) {
        Executor newExecutor = Executor.createExecutorWithNameAndType(name,type);
        listOfExecutors.value.add(newExecutor);
        System.out.println("Number of Executors: "+ listOfExecutors.value.size());
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
