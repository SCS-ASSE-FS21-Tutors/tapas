package ch.unisg.tapasexecutorpool.pool.domain;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

public class Executor {
    public enum State {
        IDLE, BUSY
    }

    @Getter
    private final ExecutorId executorId;

    @Getter
    private final ExecutorName executorName;

    @Getter
    private final ExecutorType executorType;

    @Getter
    private ExecutorState executorState;

    public Executor(ExecutorName executorName, ExecutorType executorType) {
        this.executorName = executorName;
        this.executorType = executorType;
        this.executorState = new ExecutorState(State.IDLE);
        this.executorId = new ExecutorId(UUID.randomUUID().toString());
    }

    protected static Executor createExecutorWithNameAndType(ExecutorName name, ExecutorType type) {
        System.out.println("New Executor: " + name.getValue() + " " + type.getValue());
        return new Executor(name,type);
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
}
