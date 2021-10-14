package ch.unisg.tapasexecutorpool.executorpool.domain;

import lombok.Getter;
import lombok.Setter;
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
    private final Task.TaskType taskType;

    @Getter
    private ExecutorState executorState;

    public Executor(ExecutorName executorName, Task.TaskType taskType) {
        this.executorName = executorName;
        this.taskType = taskType;
        this.executorState = new ExecutorState(State.IDLE);
        this.executorId = new ExecutorId(UUID.randomUUID().toString());
    }

    protected static Executor createExecutorWithNameAndType(ExecutorName name, Task.TaskType type) {
        //This is a simple debug message to see that the request has reached the right method in the core
        System.out.println("New Executor: " + name.getValue() + " " + type.getValue());
        return new Executor(name, type);
    }

    public void updateState(ExecutorState state) {
        this.executorState = state;
    }

    @Value
    public static class ExecutorId {
        String value;
    }

    @Value
    public static class ExecutorName {
        String value;
    }

    @Value
    public static class ExecutorState {
        State value;
    }
}
