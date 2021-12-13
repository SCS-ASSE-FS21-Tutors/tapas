package ch.unisg.tapastasks.tasks.application.service;

import ch.unisg.tapastasks.tasks.application.handler.TasksChangedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.AddNewTaskToTaskListCommand;
import ch.unisg.tapastasks.tasks.application.port.in.AddNewTaskToTaskListUseCase;
import ch.unisg.tapastasks.tasks.application.port.out.AddTaskPort;
import ch.unisg.tapastasks.tasks.application.port.out.NewTaskAddedEventPort;
import ch.unisg.tapastasks.tasks.application.port.out.TaskListLock;
import ch.unisg.tapastasks.tasks.domain.Task;

import ch.unisg.tapastasks.tasks.domain.NewTaskAddedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class AddNewTaskToTaskListService implements AddNewTaskToTaskListUseCase {


    private final String taskListName;
    private final NewTaskAddedEventPort newTaskAddedEventPort;
    private final AddTaskPort addTaskToRepositoryPort;
    private final TaskListLock taskListLock;
    private final ApplicationEventPublisher applicationEventPublisher;

    public AddNewTaskToTaskListService(@Value("${task.list.name}") String taskListName,
                                       NewTaskAddedEventPort newTaskAddedEventPort,
                                       AddTaskPort addTaskToRepositoryPort,
                                       TaskListLock taskListLock,
                                       ApplicationEventPublisher applicationEventPublisher) {
        this.taskListName = taskListName;
        this.newTaskAddedEventPort = newTaskAddedEventPort;
        this.addTaskToRepositoryPort = addTaskToRepositoryPort;
        this.taskListLock = taskListLock;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public Task addNewTaskToTaskList(AddNewTaskToTaskListCommand command) {
        taskListLock.lockTaskList(taskListName);

        Task newTask = Task.createNewTaskWithRandomTaskId(
            command.getTaskName(),
            command.getTaskType(),
            command.getInputData());

        addTaskToRepositoryPort.addTask(newTask);

        taskListLock.releaseTaskList(taskListName);

        if(applicationEventPublisher != null)
            applicationEventPublisher.publishEvent(new TasksChangedEvent(this));

        NewTaskAddedEvent newTaskAdded = new NewTaskAddedEvent(newTask, taskListName);
        newTaskAddedEventPort.publishNewTaskAddedEvent(newTaskAdded);

        return newTask;
    }
}
