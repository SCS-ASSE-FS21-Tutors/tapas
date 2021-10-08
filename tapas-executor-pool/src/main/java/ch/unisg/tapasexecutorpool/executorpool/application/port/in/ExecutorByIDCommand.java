package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

public class ExecutorByIDCommand {
    private final int executorID;


    public ExecutorByIDCommand(int executorID) {
        this.executorID = executorID;
    }

    public int getExecutorID() {
        return executorID;
    }
}
