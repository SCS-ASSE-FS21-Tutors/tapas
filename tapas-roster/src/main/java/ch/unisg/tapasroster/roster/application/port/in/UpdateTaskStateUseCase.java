package ch.unisg.tapasroster.roster.application.port.in;

import ch.unisg.tapascommon.tasks.domain.Task;

public interface UpdateTaskStateUseCase {
    Task updateTask(UpdateTaskStateCommand command);
}
