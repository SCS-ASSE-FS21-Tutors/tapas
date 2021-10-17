package ch.unisg.tapasexecutorpool.pool.domain;

public class ForwardTaskToExecutorEvent {
    public Task task;
    public Executor executor;

    public ForwardTaskToExecutorEvent(Task task, Executor executor) {
        this.task = task;
        this.executor = executor;
    }
}
