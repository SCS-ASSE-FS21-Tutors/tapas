package ch.unisg.executorpool.domain;

import ch.unisg.executorpool.domain.ExecutorClass.ExecutorUri;
import ch.unisg.executorpool.domain.ExecutorClass.ExecutorTaskType;

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

    public ExecutorClass addNewExecutor(ExecutorUri executorUri, ExecutorTaskType executorTaskType){
        ExecutorClass newExecutor = ExecutorClass.createExecutorClass(executorUri, executorTaskType);
        listOfExecutors.value.add(newExecutor);
        System.out.println("Number of executors: " + listOfExecutors.value.size());
        return newExecutor;
    }

    public Optional<ExecutorClass> getExecutorByUri(ExecutorUri executorUri){

        for (ExecutorClass executor : listOfExecutors.value ) {
            if(executor.getExecutorUri().getValue().equals(executorUri)){
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

    public Optional<ExecutorClass> removeExecutorByIpAndPort(ExecutorUri executorUri){
        for (ExecutorClass executor : listOfExecutors.value ) {
            // TODO can this be simplified by overwriting equals()?
            if(executor.getExecutorUri().getValue().equals(executorUri.getValue())){
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
