package ch.unisg.tapasroster.roster.application.service;

import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToRosterCommand;
import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToRosterUseCase;
import ch.unisg.tapasroster.roster.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Transactional
public class AssignTaskToRosterService implements AssignTaskToRosterUseCase {

    @Override
    public Optional<TaskAssignmentReply> assignTaskToRoster(AssignTaskToRosterCommand command) {
        Roster roster = Roster.getTapasRoster();

        return roster.assignTask(command.getTask());
    }
}
