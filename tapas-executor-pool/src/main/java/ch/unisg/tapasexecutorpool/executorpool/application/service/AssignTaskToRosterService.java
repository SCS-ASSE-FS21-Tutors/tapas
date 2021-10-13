package ch.unisg.tapasexecutorpool.executorpool.application.service;

import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AssignTaskToRosterCommand;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AssignTaskToRosterUseCase;
import ch.unisg.tapasexecutorpool.executorpool.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class AssignTaskToRosterService implements AssignTaskToRosterUseCase {

    @Override
    public Task assignTaskToRoster(AssignTaskToRosterCommand command) {
        Roster roster = Roster.getTapasRoster();

        return roster.assignTask(command.getTask());
    }
}
