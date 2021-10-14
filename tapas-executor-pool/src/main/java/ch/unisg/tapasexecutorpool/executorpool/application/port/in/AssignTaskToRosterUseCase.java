package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import ch.unisg.tapasexecutorpool.executorpool.domain.TaskAssignmentReply;

import java.util.Optional;

public interface AssignTaskToRosterUseCase {
    Optional<TaskAssignmentReply> assignTaskToRoster(AssignTaskToRosterCommand command);
}
