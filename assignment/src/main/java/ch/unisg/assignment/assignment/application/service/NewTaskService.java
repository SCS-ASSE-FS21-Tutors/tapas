package ch.unisg.assignment.assignment.application.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import ch.unisg.assignment.assignment.application.port.in.NewTaskCommand;
import ch.unisg.assignment.assignment.application.port.in.NewTaskUseCase;
import ch.unisg.assignment.assignment.application.port.out.GetAllExecutorInExecutorPoolByTypePort;
import ch.unisg.assignment.assignment.application.port.out.NewTaskEventPort;
import ch.unisg.assignment.assignment.domain.Roster;
import ch.unisg.assignment.assignment.domain.Task;
import ch.unisg.assignment.assignment.domain.event.NewTaskEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Transactional
public class NewTaskService implements NewTaskUseCase {

    private final NewTaskEventPort newTaskEventPort;
    private final GetAllExecutorInExecutorPoolByTypePort getAllExecutorInExecutorPoolByTypePort;

    @Override
    public boolean addNewTaskToQueue(NewTaskCommand command) {

        if (!getAllExecutorInExecutorPoolByTypePort.doesExecutorTypeExist(command.getTaskType())) {
            return false;
        }

        Task task = new Task(command.getTaskID(), command.getTaskType());

        Roster.getInstance().addTaskToQueue(task);

        // TODO this event should be in the roster function xyz
        NewTaskEvent newTaskEvent = new NewTaskEvent(task.getTaskType());
        newTaskEventPort.publishNewTaskEvent(newTaskEvent);

        return true;
    }

}
