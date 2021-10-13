package ch.unisg.tapasroster.roster.application.port.in;

import ch.unisg.tapasroster.roster.domain.Task;

public interface AssignTaskToExecutorInExecutorPoolUseCase {
    Task assignTaskToExecutor(AssignTaskToExecutorInExecutorPoolCommand command);

}
