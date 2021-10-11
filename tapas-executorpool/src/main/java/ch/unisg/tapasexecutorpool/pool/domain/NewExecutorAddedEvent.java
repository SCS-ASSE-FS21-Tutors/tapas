package ch.unisg.tapasexecutorpool.pool.domain;

/**This is a domain event (these are usually much fatter)**/
public class NewExecutorAddedEvent {
    public String executorName;
    public String executorPoolName;

    public NewExecutorAddedEvent(String executorName, String executorPoolName) {
        this.executorName = executorName;
        this.executorPoolName = executorPoolName;
    }
}
