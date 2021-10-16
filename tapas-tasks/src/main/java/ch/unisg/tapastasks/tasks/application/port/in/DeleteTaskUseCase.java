package ch.unisg.tapastasks.tasks.application.port.in;

import ch.unisg.tapastasks.tasks.domain.Task;

import java.util.Optional;

public interface DeleteTaskUseCase {
    Optional<Task> deleteTask(DeleteTaskCommand command);
}
