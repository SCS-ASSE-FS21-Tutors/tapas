package ch.unisg.executorpool.domain;

import lombok.Getter;
import lombok.Value;

public class ExecutorClass {

    @Getter
    private final ExecutorIp executorIp;

    @Getter
    private final ExecutorPort executorPort;

    @Getter
    private final ExecutorTaskType executorTaskType;

    public ExecutorClass(ExecutorIp executorIp, ExecutorPort executorPort, ExecutorTaskType executorTaskType){
        this.executorIp = executorIp;
        this.executorPort = executorPort;
        this.executorTaskType = executorTaskType;
    }

    protected static ExecutorClass createExecutorClass(ExecutorIp executorIp, ExecutorPort executorPort, ExecutorTaskType executorTaskType){
        System.out.println("New Task: " + executorIp.getValue() + " " + executorPort.getValue() + " " + executorTaskType.getValue());
        return new ExecutorClass(executorIp, executorPort, executorTaskType);
    }

    @Value
    public static class ExecutorIp {
        private String value;
    }

    @Value
    public static class ExecutorPort {
        private String value;
    }

    @Value
    public static class ExecutorTaskType {
        private String value;
    }
}
