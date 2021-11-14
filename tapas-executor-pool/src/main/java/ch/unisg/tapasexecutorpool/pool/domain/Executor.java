package ch.unisg.tapasexecutorpool.pool.domain;

import ch.unisg.tapascommon.tasks.domain.Task;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.UUID;

public class Executor {
    public enum State {
        IDLE, BUSY, RESERVED
    }

    @Getter
    private final ExecutorId executorId;

    @Getter
    private final ExecutorName executorName;

    @Getter
    private final ExecutorType executorType;

    @Getter
    private final ExecutorAddress executorAddress;

    @Setter
    @Getter
    private ExecutorState executorState;

    public Executor(
            ExecutorId executorId,
            ExecutorName executorName,
            ExecutorType executorType,
            ExecutorAddress executorAddress,
            ExecutorState executorState) {
        this.executorId = executorId;
        this.executorName = executorName;
        this.executorType = executorType;
        this.executorAddress = executorAddress;
        this.executorState = executorState;
    }

    public Executor(ExecutorName executorName, ExecutorType executorType, ExecutorAddress executorAddress) {
        this.executorName = executorName;
        this.executorType = executorType;
        this.executorAddress = executorAddress;
        this.executorState = new ExecutorState(State.IDLE);
        this.executorId = new ExecutorId(UUID.randomUUID().toString());
    }

    protected static Executor createExecutor(ExecutorName name, ExecutorType type, ExecutorAddress address) {
        System.out.println("New Executor: " + name.getValue() + " " + type.getValue());
        return new Executor(name, type, address);
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
        private Task.Type value;
    }

    @Value
    public static class ExecutorAddress {
        private String value;
    }
}
