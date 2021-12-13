package ch.unisg.tapastasks.tasks.application.handler;

import ch.unisg.tapastasks.tasks.application.port.in.TaskStartedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskStartedEventHandler;
import ch.unisg.tapastasks.tasks.application.port.out.TaskListLock;
import ch.unisg.tapastasks.tasks.application.port.out.UpdateTaskPort;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaskStartedHandler implements TaskStartedEventHandler {
    @Autowired
    UpdateTaskPort updateTaskPort;

    @Value("${task.list.name}")
    String taskListName;

    @Autowired
    TaskListLock taskListLock;

    @Override
    public Task handleTaskStarted(TaskStartedEvent taskStartedEvent) throws TaskNotFoundException {

        taskListLock.lockTaskList(taskListName);
        Task task = updateTaskPort.updateTask(taskStartedEvent.getTaskId(),
            new Task.TaskStatus(Task.Status.RUNNING), taskStartedEvent.getServiceProvider(), Optional.empty());
        taskListLock.releaseTaskList(taskListName);

        return task;
    }
}
