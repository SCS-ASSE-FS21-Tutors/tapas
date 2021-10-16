package ch.unisg.tapasroster.roster.application.service;

import ch.unisg.tapasroster.roster.application.port.in.TaskExecutionFinishedCommand;
import ch.unisg.tapasroster.roster.application.port.in.TaskExecutionFinishedUseCase;
import ch.unisg.tapasroster.roster.domain.Roster;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class TaskExecutionFinishedService implements TaskExecutionFinishedUseCase {


    @Override
    public void notifyRosterTaskExecutionFinished(TaskExecutionFinishedCommand command) {
        Roster roster = Roster.getTapasRoster();
        roster.taskExecutionFinished(command.getTaskFinishedReply());
    }
}
