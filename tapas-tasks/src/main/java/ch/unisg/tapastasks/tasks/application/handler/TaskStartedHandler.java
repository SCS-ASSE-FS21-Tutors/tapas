package ch.unisg.tapastasks.tasks.application.handler;

import ch.unisg.tapastasks.tasks.application.port.in.TaskStartedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskStartedEventHandler;
import ch.unisg.tapastasks.tasks.application.port.out.TaskListLock;
import ch.unisg.tapastasks.tasks.application.port.out.UpdateTaskPort;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaskStartedHandler implements TaskStartedEventHandler {

    private final String taskListName;
    private final UpdateTaskPort updateTaskPort;
    private final TaskListLock taskListLock;
    private final ApplicationEventPublisher applicationEventPublisher;

    public TaskStartedHandler(@Value("${task.list.name}") String taskListName,
                               UpdateTaskPort updateTaskPort,
                               TaskListLock taskListLock,
                               ApplicationEventPublisher applicationEventPublisher) {
        this.taskListName = taskListName;
        this.updateTaskPort = updateTaskPort;
        this.taskListLock = taskListLock;
        this.applicationEventPublisher = applicationEventPublisher;
    }
    @Override
    public Task handleTaskStarted(TaskStartedEvent taskStartedEvent) throws TaskNotFoundException {

        taskListLock.lockTaskList(taskListName);
        Task task = updateTaskPort.updateTask(taskStartedEvent.getTaskId(),
            new Task.TaskStatus(Task.Status.RUNNING), taskStartedEvent.getServiceProvider(), Optional.empty());
        taskListLock.releaseTaskList(taskListName);

        applicationEventPublisher.publishEvent(new TasksChangedEvent(this));

        return task;
    }
}
