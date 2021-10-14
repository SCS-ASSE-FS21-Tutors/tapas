package ch.unisg.tapasexecutorpool.pool.domain;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

/**This is a domain entity**/
public class Executor {
    public enum State {
        OPEN, ASSIGNED, RUNNING, EXECUTED
    }

    @Getter
    private final ExecutorId executorId;

    @Getter
    private final ExecutorName executorName;

    @Getter
    private final ExecutorType executorType;

    @Getter
    private ExecutorState executorState;

    @Getter
    private final ExecutorPort executorPort;

    public Executor(ExecutorName executorName, ExecutorType executorType, ExecutorPort executorPort) {
        this.executorName = executorName;
        this.executorType = executorType;
        this.executorState = new ExecutorState(State.OPEN);
        this.executorId = new ExecutorId(UUID.randomUUID().toString());
        this.executorPort = executorPort;
    }

    protected static Executor createExecutor(ExecutorName name, ExecutorType type, ExecutorPort port) {
        //This is a simple debug message to see that the request has reached the right method in the core
        System.out.println("New Executor: " + name.getValue() + " " + type.getValue()+ " " + port.getValue());
        return new Executor(name,type, port);
    }

    @Value
    public static class ExecutorId {
        private String value;
    }

    @Value
    public static class ExecutorName {
        private String value;
    }

    @Value
    public static class ExecutorState {
        private State value;
    }

    @Value
    public static class ExecutorType {
        private String value;
    }

    @Value
    public static class ExecutorPort {
        private String value;
    }
}
