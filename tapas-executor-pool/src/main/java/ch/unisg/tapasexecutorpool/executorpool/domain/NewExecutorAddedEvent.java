package ch.unisg.tapasexecutorpool.executorpool.domain;


public class NewExecutorAddedEvent {
    public String executorName;
    public String executorListName;

    public NewExecutorAddedEvent(String executorName, String executorListName) {
        this.executorName = executorName;
        this.executorListName = executorListName;
    }
}
