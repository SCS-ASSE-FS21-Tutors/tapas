package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import ch.unisg.tapasexecutorpool.executorpool.domain.Task;

public interface AssignTaskToRosterUseCase {
    Task assignTaskToRoster(AssignTaskToRosterCommand command);
}
