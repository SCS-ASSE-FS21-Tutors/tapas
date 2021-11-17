package ch.unisg.tapastasks.tasks.application.service;

import ch.unisg.tapastasks.tasks.application.port.in.AddNewTaskToTaskListCommand;
import ch.unisg.tapastasks.tasks.application.port.in.AddNewTaskToTaskListUseCase;
import ch.unisg.tapastasks.tasks.application.port.out.NewTaskAddedEventPort;
import ch.unisg.tapastasks.tasks.domain.Task;

import ch.unisg.tapastasks.tasks.domain.NewTaskAddedEvent;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class AddNewTaskToTaskListService implements AddNewTaskToTaskListUseCase {

    private final NewTaskAddedEventPort newTaskAddedEventPort;

    @Override
    public Task addNewTaskToTaskList(AddNewTaskToTaskListCommand command) {
        TaskList taskList = TaskList.getTapasTaskList();

        Task newTask;

        if (command.getOriginalTaskUri().isPresent() && command.getInputData().isPresent()) {
            newTask = taskList.addNewTaskWithNameAndTypeAndOriginalTaskUriAndInputData(command.getTaskName(),
                command.getTaskType(), command.getOriginalTaskUri().get(), command.getInputData().get());
        } else if (command.getOriginalTaskUri().isPresent()) {
            newTask = taskList.addNewTaskWithNameAndTypeAndOriginalTaskUri(command.getTaskName(),
                command.getTaskType(), command.getOriginalTaskUri().get());
        } else if (command.getInputData().isPresent()) {
            newTask = taskList.addNewTaskWithNameAndTypeAndInputData(command.getTaskName(),
            command.getTaskType(), command.getInputData().get());
        } else {
            newTask = taskList.addNewTaskWithNameAndType(command.getTaskName(), command.getTaskType());
        }

        //Here we are using the application service to emit the domain event to the outside of the bounded context.
        //This event should be considered as a light-weight "integration event" to communicate with other services.
        //Domain events are usually rather "fat". In our implementation we simplify at this point. In general, it is
        //not recommended to emit a domain event via an application service! You should first emit the domain event in
        //the core and then the integration event in the application layer.
        if (newTask != null) {
            NewTaskAddedEvent newTaskAdded = new NewTaskAddedEvent(
                newTask.getTaskName().getValue(),
                taskList.getTaskListName().getValue(),
                newTask.getTaskId().getValue(),
                newTask.getTaskType().getValue(),
                newTask.getInputData().getValue()
            );
            newTaskAddedEventPort.publishNewTaskAddedEvent(newTaskAdded);
        }

        return newTask;
    }
}
