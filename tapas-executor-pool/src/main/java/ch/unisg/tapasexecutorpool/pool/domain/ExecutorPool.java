package ch.unisg.tapasexecutorpool.pool.domain;

import ch.unisg.tapascommon.ServiceHostAddresses;
import ch.unisg.tapascommon.tasks.domain.Task;
import lombok.Getter;
import lombok.Value;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ExecutorPool {

    private static final String EXECUTOR_API_CALC = ServiceHostAddresses.getExecutorCalcServiceHostAddress() + "/execute-task";
    private static final String EXECUTOR_API_ROBOT = ServiceHostAddresses.getExecutorRobotServiceHostAddress() + "/execute-task";

    private static final ExecutorPool EXECUTOR_POOL = new ExecutorPool(new ExecutorPoolName("tapas-executorpool"));

    @Getter
    private final ExecutorPoolName executorPoolName;

    @Getter
    private final ListOfExecutors listOfExecutors;

    private ExecutorPool(ExecutorPoolName executorPoolName) {
        this.executorPoolName = executorPoolName;
        this.listOfExecutors = new ListOfExecutors(new LinkedList<>());

        // Temporary until Executors are dynamically added to the pool
        addNewExecutor(new Executor.ExecutorName("Calculator"), new Executor.ExecutorType(Task.Type.COMPUTATION), new Executor.ExecutorAddress(EXECUTOR_API_CALC));
        addNewExecutor(new Executor.ExecutorName("Robot"), new Executor.ExecutorType(Task.Type.BIGROBOT), new Executor.ExecutorAddress(EXECUTOR_API_ROBOT));
    }

    public static ExecutorPool getTapasExecutorPool() {
        return EXECUTOR_POOL;
    }

    public Executor addNewExecutor(Executor.ExecutorName name, Executor.ExecutorType type, Executor.ExecutorAddress address) {
        var newExecutor = Executor.createExecutor(name, type, address);
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

    public Optional<Executor> retrieveAvailableExecutorByTaskType(Task.TaskType type) {
        for (Executor executor : listOfExecutors.value) {
            if (executor.getExecutorType().getValue().name().equalsIgnoreCase(type.getValue().name())) {
                if (Objects.equals(executor.getExecutorState(), new Executor.ExecutorState(Executor.State.IDLE))) {
                    return Optional.of(executor);
                }
            }
        }
        return Optional.empty();
    }

    public boolean updateExecutorState(Executor.ExecutorId executorId, Executor.ExecutorState executorState) {
        var executor = retrieveExecutorById(executorId);
        if (executor.isPresent()) {
            executor.get().setExecutorState(executorState);
            return true;
        }
        return false;
    }

    // Temporary until Executors are dynamically added to the pool
    public Executor.ExecutorId getExecutorIdForExecutorType(String executorType) {
        for (Executor executor : listOfExecutors.value) {
            if (executor.getExecutorType().getValue().name().equalsIgnoreCase(executorType)) {
                return executor.getExecutorId();
            }
        }
        return null;
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
