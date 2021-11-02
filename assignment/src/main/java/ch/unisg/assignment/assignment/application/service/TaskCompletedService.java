package ch.unisg.assignment.assignment.application.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import ch.unisg.assignment.assignment.application.port.in.TaskCompletedCommand;
import ch.unisg.assignment.assignment.application.port.in.TaskCompletedUseCase;
import ch.unisg.assignment.assignment.application.port.out.TaskCompletedEventPort;
import ch.unisg.assignment.assignment.domain.Roster;
import ch.unisg.assignment.assignment.domain.event.TaskCompletedEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Transactional
public class TaskCompletedService implements TaskCompletedUseCase {

    private final TaskCompletedEventPort taskCompletedEventPort;

    /**
    *   Completes the task in the roster and publishes a task completed event.
    *   @return void
    **/
    @Override
    public void taskCompleted(TaskCompletedCommand command) {

        Roster.getInstance().taskCompleted(command.getTaskID());

        taskCompletedEventPort.publishTaskCompleted(new TaskCompletedEvent(command.getTaskID(),
            command.getTaskStatus(), command.getTaskResult()));

    }

}
