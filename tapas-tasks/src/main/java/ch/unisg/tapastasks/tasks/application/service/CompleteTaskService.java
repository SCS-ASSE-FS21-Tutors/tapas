package ch.unisg.tapastasks.tasks.application.service;

import ch.unisg.tapastasks.tasks.application.port.in.CompleteTaskCommand;
import ch.unisg.tapastasks.tasks.application.port.in.CompleteTaskUseCase;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.Task.*;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Transactional
public class CompleteTaskService implements CompleteTaskUseCase {

    @Override
    public Task completeTask(CompleteTaskCommand command){
        // TODO Retrieve the task based on ID
        TaskList taskList = TaskList.getTapasTaskList();
        Optional<Task> updatedTask = taskList.retrieveTaskById(command.getTaskId());

        // TODO Update the status and result (and save?)
        Task newTask = updatedTask.get();
        newTask.taskResult = new TaskResult(command.getTaskResult().getValue());
        newTask.taskState = new TaskState(Task.State.EXECUTED);


        // TODO return the updated task
        return newTask;
    }
}
