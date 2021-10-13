package ch.unisg.tapasexecutors.executors.domain;

/** domain event for adding executor **/
public class NewExecutorAddedEvent {
    public String executorName;

    public NewExecutorAddedEvent(String executorName) {
        this.executorName = executorName;
    }
}