package ch.unisg.roster.roster.application.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import ch.unisg.roster.roster.application.port.in.NewTaskCommand;
import ch.unisg.roster.roster.application.port.in.NewTaskUseCase;
import ch.unisg.roster.roster.application.port.out.NewTaskEventPort;
import ch.unisg.roster.roster.domain.ExecutorRegistry;
import ch.unisg.roster.roster.domain.Roster;
import ch.unisg.roster.roster.domain.Task;
import ch.unisg.roster.roster.domain.event.NewTaskEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Transactional
public class NewTaskService implements NewTaskUseCase {

    private final NewTaskEventPort newTaskEventPort;

    /**
    *   Checks if we can execute the give task, if yes the task gets added to the task queue and return true.
    *   If the task can not be executed by an internal or auction house executor, the method return false.
    *   @return boolean
    **/
    @Override
    public boolean addNewTaskToQueue(NewTaskCommand command) {

        ExecutorRegistry executorRegistry = ExecutorRegistry.getInstance();

        if (!executorRegistry.containsTaskType(command.getTaskType())) {
            return false;
        }

        Task task = new Task(command.getTaskID(), command.getTaskType(), command.getInputData());

        Roster.getInstance().addTaskToQueue(task);

        NewTaskEvent newTaskEvent = new NewTaskEvent(task.getTaskType());
        newTaskEventPort.publishNewTaskEvent(newTaskEvent);

        return true;
    }

}
