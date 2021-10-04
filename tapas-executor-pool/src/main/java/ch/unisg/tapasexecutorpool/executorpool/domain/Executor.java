package ch.unisg.tapasexecutorpool.executorpool.domain;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

/**This is a domain entity**/
public class Executor {
    public enum State {
        IDLE, RUNNING
    }

    @Getter
    private final ExecutorId executorId;

    @Getter
    private final ExecutorName executorName;

    @Getter
    private final TaskType taskType;

    @Getter
    private ExecutorState executorState;

    public Executor(ExecutorName executorName, TaskType taskType) {
        this.executorName = executorName;
        this.taskType = taskType;
        this.executorState = new ExecutorState(State.IDLE);
        this.executorId = new ExecutorId(UUID.randomUUID().toString());
    }

    protected static Executor createTaskWithNameAndType(ExecutorName name, TaskType type) {
        //This is a simple debug message to see that the request has reached the right method in the core
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
    public static class TaskType {
        private String value;
    }
}
