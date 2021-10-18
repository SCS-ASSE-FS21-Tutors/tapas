package ch.unisg.tapastasks.tasks.application.port.in;

import ch.unisg.tapastasks.tasks.domain.Task;

public interface UpdateTaskStateUseCase {
    Task modifyTask(UpdateTaskStateCommand command);
}
