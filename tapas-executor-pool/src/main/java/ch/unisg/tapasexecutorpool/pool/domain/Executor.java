package ch.unisg.tapasexecutorpool.pool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.UUID;

/**
 * This is a domain entity
 **/
@EqualsAndHashCode
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class Executor {
    public enum State {
        AVAILABLE, OCCUPIED
    }

    @Getter
    private final ExecutorId executorId;

    @Getter
    private final ExecutorName executorName;

    @Getter
    private final ExecutorType executorType;

    @Getter
    @Setter
    private ExecutorState executorState;

    @JsonIgnore
    @Getter
    @Setter
    private Task assignedTask;


    @Getter
    private final ExecutorUrl executorUrl;

    public Executor(ExecutorName executorName, ExecutorType executorType, ExecutorUrl executorUrl) {
        this.executorName = executorName;
        this.executorType = executorType;
        this.executorState = new ExecutorState(State.AVAILABLE);
        this.assignedTask = null;
        this.executorId = new ExecutorId(UUID.randomUUID().toString());
        this.executorUrl = executorUrl;
    }

    protected static Executor createExecutor(ExecutorName name, ExecutorType type, ExecutorUrl url) {
        //This is a simple debug message to see that the request has reached the right method in the core
        System.out.println("New Executor: " + name.getValue() + " " + type.getValue() + " " + url.getValue());
        return new Executor(name, type, url);
    }

    @Value
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class ExecutorId {
        private String value;
    }

    @Value
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class ExecutorName {
        private String value;
    }

    @Value
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class ExecutorState {
        private State value;
    }

    @Value
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class ExecutorType {
        private String value;
    }

    @Value
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class ExecutorUrl {
        private String value;
    }

    @Override
    public String toString() {
        return executorName.value + " | " + executorState.value + " | " + executorUrl.value;
    }
}
