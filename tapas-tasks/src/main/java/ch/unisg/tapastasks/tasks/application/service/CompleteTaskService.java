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
        TaskList taskList = TaskList.getTapasTaskList();
        Optional<Task> updatedTask = taskList.retrieveTaskById(command.getTaskId());

        Task newTask = updatedTask.get();
        newTask.taskResult = new TaskResult(command.getTaskResult().getValue());
        newTask.taskStatus = new TaskStatus(Task.Status.EXECUTED);

        return newTask;
    }
}
