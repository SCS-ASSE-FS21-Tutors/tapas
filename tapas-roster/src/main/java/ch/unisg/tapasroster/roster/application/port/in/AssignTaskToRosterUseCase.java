package ch.unisg.tapasroster.roster.application.port.in;

import ch.unisg.tapasroster.roster.domain.TaskAssignmentReply;

import java.util.Optional;

public interface AssignTaskToRosterUseCase {
    Optional<TaskAssignmentReply> assignTaskToRoster(AssignTaskToRosterCommand command);
}
