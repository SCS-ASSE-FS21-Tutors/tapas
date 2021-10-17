package ch.unisg.tapastasks.tasks.application.port.in;

import ch.unisg.tapastasks.tasks.domain.Task;

public interface TaskAssignedUseCase {
    Task assignTask(TaskAssignedCommand command);
}
