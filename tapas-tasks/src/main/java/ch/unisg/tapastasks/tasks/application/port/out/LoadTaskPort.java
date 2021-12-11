package ch.unisg.tapastasks.tasks.application.port.out;

import ch.unisg.tapastasks.tasks.domain.Task;

public interface LoadTaskPort {

    Task loadTask(Task.TaskId taskId);

}
