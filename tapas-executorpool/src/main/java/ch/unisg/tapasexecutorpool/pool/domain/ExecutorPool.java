package ch.unisg.tapasexecutorpool.pool.domain;

import ch.unisg.tapascommon.ServiceApiAddresses;
import ch.unisg.tapascommon.tasks.domain.Task;
import lombok.Getter;
import lombok.Value;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ExecutorPool {

    private static final String EXECUTOR_API_CALC = ServiceApiAddresses.getExecutorCalcServiceApiUrl() + "/executor-calc/execute-task/";
    private static final String EXECUTOR_API_ROBOT = ServiceApiAddresses.getExecutorRobotServiceApiUrl() + "/executor-robot/execute-task/";

    @Getter
    private final ExecutorPoolName executorPoolName;

    @Getter
    private final ListOfExecutors listOfExecutors;

    private static final ExecutorPool EXECUTOR_POOL = new ExecutorPool(new ExecutorPoolName("tapas-executorpool"));

    private ExecutorPool(ExecutorPoolName executorPoolName) {
        this.executorPoolName = executorPoolName;
        this.listOfExecutors = new ListOfExecutors(new LinkedList<>());

        addNewExecutor(new Executor.ExecutorName("Calculator"), new Executor.ExecutorType("calc"), new Executor.ExecutorAddress(EXECUTOR_API_CALC));
        addNewExecutor(new Executor.ExecutorName("Robot"), new Executor.ExecutorType("robot"), new Executor.ExecutorAddress(EXECUTOR_API_ROBOT));
    }

    public static ExecutorPool getTapasExecutorPool() {
        return EXECUTOR_POOL;
    }

    public Executor addNewExecutor(Executor.ExecutorName name, Executor.ExecutorType type, Executor.ExecutorAddress address) {
        Executor newExecutor = Executor.createExecutor(name, type, address);
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

    public Optional<Executor> retrieveExecutorByTaskType(Task.TaskType type) {
        for (Executor executor : listOfExecutors.value) {
            if (executor.getExecutorType().getValue().equalsIgnoreCase(type.getValue())) {
                return Optional.of(executor);
            }
        }

        return Optional.empty();
    }

    @Value
    public static class ExecutorPoolName {
        String value;
    }

    @Value
    public static class ListOfExecutors {
        List<Executor> value;
    }

}
