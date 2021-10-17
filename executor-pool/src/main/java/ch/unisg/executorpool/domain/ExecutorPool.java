package ch.unisg.executorpool.domain;

import lombok.Getter;
import lombok.Value;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ExecutorPool {

    @Getter
    private final ListOfExecutors listOfExecutors;

    private static final ExecutorPool executorPool = new ExecutorPool();

    private ExecutorPool(){
        this.listOfExecutors = new ListOfExecutors(new LinkedList<ExecutorClass>());
    }

    public static ExecutorPool getExecutorPool() { return executorPool; }

    public ExecutorClass addNewExecutor(ExecutorClass.ExecutorIp executorIp, ExecutorClass.ExecutorPort executorPort, ExecutorClass.ExecutorTaskType executorTaskType){
        ExecutorClass newExecutor = ExecutorClass.createExecutorClass(executorIp, executorPort, executorTaskType);
        listOfExecutors.value.add(newExecutor);
        System.out.println("Number of executors: " + listOfExecutors.value.size());
        return newExecutor;
    }

    public Optional<ExecutorClass> getExecutorByIpAndPort(ExecutorClass.ExecutorIp executorIp, ExecutorClass.ExecutorPort executorPort){

        for (ExecutorClass executor : listOfExecutors.value ) {
            // TODO can this be simplified by overwriting equals()?
            if(executor.getExecutorIp().getValue().equalsIgnoreCase(executorIp.getValue()) &&
                    executor.getExecutorPort().getValue().equalsIgnoreCase(executorPort.getValue())){
                return Optional.of(executor);
            }
        }

        return Optional.empty();
    }

    public List<ExecutorClass> getAllExecutorsByType(ExecutorClass.ExecutorTaskType executorTaskType){

        List<ExecutorClass> matchedExecutors = new LinkedList<ExecutorClass>();

        for (ExecutorClass executor : listOfExecutors.value ) {
            // TODO can this be simplified by overwriting equals()?
            if(executor.getExecutorTaskType().getValue().equalsIgnoreCase(executorTaskType.getValue())){
                matchedExecutors.add(executor);
            }
        }

        return matchedExecutors;
    }

    public Optional<ExecutorClass> removeExecutorByIpAndPort(ExecutorClass.ExecutorIp executorIp, ExecutorClass.ExecutorPort executorPort){
        for (ExecutorClass executor : listOfExecutors.value ) {
            // TODO can this be simplified by overwriting equals()?
            if(executor.getExecutorIp().getValue().equalsIgnoreCase(executorIp.getValue()) &&
                    executor.getExecutorPort().getValue().equalsIgnoreCase(executorPort.getValue())){
                listOfExecutors.value.remove(executor);
                return Optional.of(executor);
            }
        }

        return Optional.empty();
    }

    @Value
    public static class ListOfExecutors {
        private List<ExecutorClass> value;
    }
}
