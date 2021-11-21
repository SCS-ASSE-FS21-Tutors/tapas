package ch.unisg.roster.roster.application.service;

import javax.transaction.Transactional;

import ch.unisg.roster.roster.application.port.in.DeleteRosterItem;
import org.springframework.stereotype.Component;

import ch.unisg.roster.roster.application.port.in.TaskCompletedCommand;
import ch.unisg.roster.roster.application.port.in.TaskCompletedUseCase;
import ch.unisg.roster.roster.application.port.out.TaskCompletedEventPort;
import ch.unisg.roster.roster.domain.Roster;
import ch.unisg.roster.roster.domain.event.TaskCompletedEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Transactional
public class TaskCompletedService implements TaskCompletedUseCase {

    private final TaskCompletedEventPort taskCompletedEventPort;
    private final DeleteRosterItem deleteRosterItem;

    /**
    *   Completes the task in the roster and publishes a task completed event.
    *   @return void
    **/
    @Override
    public void taskCompleted(TaskCompletedCommand command) {

        Roster.getInstance().taskCompleted(command.getTaskID());
        deleteRosterItem.deleteRosterItem(command.getTaskID());

        taskCompletedEventPort.publishTaskCompleted(new TaskCompletedEvent(command.getTaskID(),
            command.getTaskStatus(), command.getTaskResult()));

    }

}