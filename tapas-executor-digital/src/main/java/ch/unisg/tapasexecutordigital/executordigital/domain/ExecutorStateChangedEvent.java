package ch.unisg.tapasexecutordigital.executordigital.domain;

public class ExecutorStateChangedEvent {
    public String executorName;
    public String executorState;

    public ExecutorStateChangedEvent(String executorName, String executorState) {
        this.executorName = executorName;
        this.executorState = executorState;
    }
}