package ch.unisg.tapasexecutors.executors.domain;

import lombok.Getter;

import java.util.UUID;

public class RobotExecutor implements Executors {

    @Getter
    private final ExecutorName executorName;

    @Getter
    private final ExecutorType executorType;

    @Getter
    private final ExecutorState executorState;

    @Getter
    private ExecutorId executorId;


    public RobotExecutor(ExecutorName executorName, ExecutorType executorType) {
        this.executorName = executorName;
        this.executorType = executorType;
        this.executorState = new ExecutorState(State.OPEN);
        this.executorId = new ExecutorId(UUID.randomUUID().toString());
    }

    protected static RobotExecutor createExecutorWithNameAndType(ExecutorName name, ExecutorType type) {
        // for debugging
        System.out.println("New Executor: " + name.getValue() + " " + type.getValue());
        return new RobotExecutor(name,type);
    }

    @Override
    public void startTask() {
        // TODO
    }

    @Override
    public void completeTask() {
        // TODO
    }
}
