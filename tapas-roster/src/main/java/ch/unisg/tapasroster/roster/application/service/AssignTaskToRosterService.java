package ch.unisg.tapasroster.roster.application.service;

import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToRosterCommand;
import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToRosterUseCase;
import ch.unisg.tapasroster.roster.application.port.out.RunTaskEventPort;
import ch.unisg.tapasroster.roster.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Transactional
public class AssignTaskToRosterService implements AssignTaskToRosterUseCase {

    private final RunTaskEventPort runTaskEventPort;

    @Override
    public Optional<TaskAssignmentReply> assignTaskToRoster(AssignTaskToRosterCommand command) {
        Roster roster = Roster.getTapasRoster();

        Optional<TaskAssignmentReply> reply = roster.assignTask(command.getTask());

        reply.ifPresent(this::runTaskEvent);

        return reply;
    }

    @Async
    public void runTaskEvent(TaskAssignmentReply reply) {
        RunTaskEvent event = new RunTaskEvent(reply.getTask(), reply.getExecutor());
        runTaskEventPort.runTaskEvent(event);
    }
}
