package ch.unisg.tapasexecutorpool.executorpool.domain;

import lombok.Getter;
import lombok.Value;

import java.awt.*;
import java.util.UUID;

/**This is a domain entity**/
public class Executor {

    public enum State {
        FREE, OCCUPIED
    }

    @Getter
    private final ExecutorId executorId;

    @Getter
    private final ExecutorUrl executorUrl;

    @Getter
    private final ExecutorType executorType;

    @Getter
    private ExecutorState executorState;

    public Executor(ExecutorUrl executorUrl, ExecutorType executorType) {
        this.executorId = new ExecutorId(UUID.randomUUID().toString());
        this.executorUrl =  executorUrl;
        this.executorType = executorType;
        this.executorState = new ExecutorState(State.FREE);
    }


    protected static Executor createExecutorWithUrlAndType(ExecutorUrl url, ExecutorType type) {
        //This is a simple debug message to see that the request has reached the right method in the core
        System.out.println("New Executor: " + url.getValue() + " " + type.getValue());
        return new Executor(url,type);
    }

    @Value
    public static class ExecutorId {
        private String value;
    }
    @Value
    public static class ExecutorUrl {
        private String value;
    }

    @Value
    public static class ExecutorType {
        private String value;
    }

    @Value
    public static class ExecutorState {
        private State value;
    }


}