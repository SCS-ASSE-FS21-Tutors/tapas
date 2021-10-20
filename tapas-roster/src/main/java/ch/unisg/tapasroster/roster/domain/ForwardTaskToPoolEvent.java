package ch.unisg.tapasroster.roster.domain;

import ch.unisg.tapascommon.tasks.domain.Task;

public class ForwardTaskToPoolEvent {
    public Task task;

    public ForwardTaskToPoolEvent(Task task) {
        this.task = task;
    }
}
