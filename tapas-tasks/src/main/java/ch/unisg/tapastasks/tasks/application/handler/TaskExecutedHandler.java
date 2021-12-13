package ch.unisg.tapastasks.tasks.application.handler;

import ch.unisg.tapastasks.tasks.application.port.in.TaskExecutedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskExecutedEventHandler;
import ch.unisg.tapastasks.tasks.application.port.out.TaskListLock;
import ch.unisg.tapastasks.tasks.application.port.out.UpdateTaskPort;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TaskExecutedHandler implements TaskExecutedEventHandler {

    private final String taskListName;
    private final UpdateTaskPort updateTaskPort;
    private final TaskListLock taskListLock;

    public TaskExecutedHandler(@Value("${task.list.name}") String taskListName,
                               UpdateTaskPort updateTaskPort,
                               TaskListLock taskListLock) {
        this.taskListName = taskListName;
        this.updateTaskPort = updateTaskPort;
        this.taskListLock = taskListLock;
    }
    @Override
    public Task handleTaskExecuted(TaskExecutedEvent taskExecutedEvent) throws TaskNotFoundException {

        taskListLock.lockTaskList(taskListName);
        Task task = updateTaskPort.updateTask(taskExecutedEvent.getTaskId(),
            new Task.TaskStatus(Task.Status.EXECUTED), taskExecutedEvent.getServiceProvider(), taskExecutedEvent.getOutputData());
        taskListLock.releaseTaskList(taskListName);

        return task;
    }
}
