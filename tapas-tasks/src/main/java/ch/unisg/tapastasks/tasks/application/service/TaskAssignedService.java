package ch.unisg.tapastasks.tasks.application.service;

import ch.unisg.tapastasks.tasks.application.port.in.TaskAssignedCommand;
import ch.unisg.tapastasks.tasks.application.port.in.TaskAssignedUseCase;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.Task.*;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Transactional
public class TaskAssignedService implements TaskAssignedUseCase {

    @Override
    public Task assignTask(TaskAssignedCommand command) {
        // retrieve the task based on ID
        TaskList taskList = TaskList.getTapasTaskList();
        Optional<Task> task = taskList.retrieveTaskById(command.getTaskId());

        // update the status to assigned
        Task updatedTask = task.get();
        updatedTask.taskState = new TaskState(State.ASSIGNED);

        return updatedTask;
    }
}
