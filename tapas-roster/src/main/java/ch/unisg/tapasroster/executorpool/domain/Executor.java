package ch.unisg.tapasroster.executorpool.domain;

import ch.unisg.tapasroster.roster.domain.Task;
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
    private final ExecutorServer executorServer;

    @Getter
    private final ExecutorPort executorPort;

    @Getter
    private final Task.TaskType taskType;

    @Getter
    private ExecutorState executorState;

    public Executor(ExecutorName executorName,
                    ExecutorServer executorServer,
                    ExecutorPort executorPort,
                    Task.TaskType taskType) {
        this.executorName = executorName;
        this.executorServer = executorServer;
        this.executorPort = executorPort;
        this.taskType = taskType;
        this.executorState = new ExecutorState(State.IDLE);
        this.executorId = new ExecutorId(UUID.randomUUID().toString());
    }

    protected static Executor createExecutorWithNameAndType(ExecutorName name,
                                                            ExecutorServer server,
                                                            ExecutorPort port,
                                                            Task.TaskType type) {
        //This is a simple debug message to see that the request has reached the right method in the core
        System.out.println("New Executor: " + name.getValue() + " " + type.getValue());
        return new Executor(name, server, port, type);
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
    public static class ExecutorServer {
        String value;
    }

    @Value
    public static class ExecutorPort {
        String value;
    }

    @Value
    public static class ExecutorState {
        State value;
    }
}
