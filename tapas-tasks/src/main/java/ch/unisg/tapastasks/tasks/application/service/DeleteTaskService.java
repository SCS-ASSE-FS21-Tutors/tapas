package ch.unisg.tapastasks.tasks.application.service;


import ch.unisg.tapastasks.tasks.application.port.in.DeleteTaskCommand;
import ch.unisg.tapastasks.tasks.application.port.in.DeleteTaskUseCase;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Transactional
public class DeleteTaskService implements DeleteTaskUseCase {

    @Override
    public Optional<Task> deleteTask(DeleteTaskCommand command){

        TaskList taskList = TaskList.getTapasTaskList();
        Optional<Task> updatedTask = taskList.retrieveTaskById(command.getTaskId());
        Task newTask = updatedTask.get();
        // TODO: Fill in the right condition into the if-statement and the else-statement
        if (true){
            return taskList.deleteTaskById(command.getTaskId());
        }
        // TODO Handle with a return message
        return Optional.empty();
    }
}
