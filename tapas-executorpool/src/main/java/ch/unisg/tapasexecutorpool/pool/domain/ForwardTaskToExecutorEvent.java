package ch.unisg.tapasexecutorpool.pool.domain;

public class ForwardTaskToExecutorEvent {
    public Task task;

    public ForwardTaskToExecutorEvent(Task task) {
        this.task = task;
    }
}
