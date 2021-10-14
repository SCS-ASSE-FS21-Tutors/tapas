package ch.unisg.tapasexecutorpool.executorpool.domain;

import lombok.Getter;
import lombok.Value;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class ExecutorPool {

    @Getter
    private final ExecutorPoolName executorPoolName;

    @Getter
    private final PoolOfExecutors poolOfExecutors;

    private static final ExecutorPool executorPool = new ExecutorPool(new ExecutorPoolName("tapas-executor-pool"));

    private ExecutorPool(ExecutorPoolName executorPoolName) {
        this.executorPoolName = executorPoolName;
        this.poolOfExecutors = new PoolOfExecutors(new LinkedList<>());
    }

    public static ExecutorPool getTapasExecutorPool() {
        return executorPool;
    }

    public Executor addNewExecutorWithNameAndType(Executor.ExecutorName name, Task.TaskType type) {
        Executor newExecutor = Executor.createExecutorWithNameAndType(name, type);
        poolOfExecutors.value.add(newExecutor);
        //This is a simple debug message to see that the task list is growing with each new request
        System.out.println("Number of executors: "+ poolOfExecutors.value.size());

        return newExecutor;
    }

    public List<Executor> getExecutorsOfType(Task.TaskType taskType) {
        return poolOfExecutors.value.stream()
                .filter(e -> e.getTaskType().equals(taskType))
                .collect(Collectors.toList());
    }

    @Value
    public static class ExecutorPoolName {
        String value;
    }

    @Value
    public static class PoolOfExecutors {
        List<Executor> value;
    }

}
