package ch.unisg.tapasexecutorpool.pool.domain;

/**This is a domain event (these are usually much fatter)**/
public class NewExecutorAddedEvent {
    public String executorName;
    public String executorListName;

    public NewExecutorAddedEvent(String executorName, String executorListName) {
        this.executorName = executorName;
        this.executorListName = executorListName;
    }
}
