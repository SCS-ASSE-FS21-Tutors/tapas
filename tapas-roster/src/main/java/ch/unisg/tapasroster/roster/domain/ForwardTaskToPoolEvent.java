package ch.unisg.tapasroster.roster.domain;

public class ForwardTaskToPoolEvent {
    public Task task;

    public ForwardTaskToPoolEvent(Task task) {
        this.task = task;
    }
}
