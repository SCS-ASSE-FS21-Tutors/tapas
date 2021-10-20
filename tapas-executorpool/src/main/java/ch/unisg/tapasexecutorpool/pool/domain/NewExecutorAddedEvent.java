package ch.unisg.tapasexecutorpool.pool.domain;

public class NewExecutorAddedEvent {
    public String executorName;
    public String executorPoolName;

    public NewExecutorAddedEvent(String executorName, String executorPoolName) {
        this.executorName = executorName;
        this.executorPoolName = executorPoolName;
    }
}
