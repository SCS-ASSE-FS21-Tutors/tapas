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
        var taskList = TaskList.getTapasTaskList();

        var newTask = (command.getOriginalTaskUri().isPresent()) ?
            // Create a delegated task that points back to the original task
            taskList.addNewTaskWithNameAndTypeAndOriginalTaskUri(command.getTaskName(),
                command.getTaskType(), command.getOriginalTaskUri().get())
            // Create an original task
            : taskList.addNewTaskWithNameAndType(command.getTaskName(), command.getTaskType());

        // When creating a task, the task's representation may include optional input data
        if (command.getInputData().isPresent()){
            newTask.setInputData(command.getInputData().get());
        }

        // Here we are using the application service to emit the domain event to the outside of the bounded context.
        // This event should be considered as a light-weight "integration event" to communicate with other services.
        // Domain events are usually rather "fat". In our implementation we simplify at this point. In general, it is
        // not recommended to emit a domain event via an application service! You should first emit the domain event in
        // the core and then the integration event in the application layer.
        if (newTask != null) {
            var newTaskAdded = new NewTaskAddedEvent(
                newTask.getTaskName().getValue(),
                taskList.getTaskListName().getValue(),
                newTask.getTaskId().getValue()
            );
            newTaskAddedEventPort.publishNewTaskAddedEvent(newTaskAdded);
        }

        return newTask;
    }
}
