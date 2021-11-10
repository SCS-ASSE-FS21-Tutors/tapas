package ch.unisg.executorpool.domain;

import lombok.Getter;
import lombok.Value;

import java.net.URI;

public class ExecutorClass {

    @Getter
    private final ExecutorUri executorUri;

    @Getter
    private final ExecutorTaskType executorTaskType;

    public ExecutorClass(ExecutorUri executorUri, ExecutorTaskType executorTaskType){
        this.executorUri = executorUri;
        this.executorTaskType = executorTaskType;
    }

    protected static ExecutorClass createExecutorClass(ExecutorUri executorUri, ExecutorTaskType executorTaskType){
        System.out.println("New Executor: " + executorUri.value.toString() + " " + executorTaskType.getValue());
        return new ExecutorClass(executorUri, executorTaskType);
    }

    @Value
    public static class ExecutorUri {
        private URI value;
    }

    @Value
    public static class ExecutorTaskType {
        private String value;
    }
}
