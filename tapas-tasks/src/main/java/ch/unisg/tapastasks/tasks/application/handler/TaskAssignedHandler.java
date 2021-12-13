package ch.unisg.tapastasks.tasks.application.handler;

import ch.unisg.tapastasks.tasks.application.port.in.TaskAssignedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskAssignedEventHandler;
import ch.unisg.tapastasks.tasks.application.port.out.TaskListLock;
import ch.unisg.tapastasks.tasks.application.port.out.UpdateTaskPort;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@Transactional
public class TaskAssignedHandler implements TaskAssignedEventHandler {

    private final String taskListName;
    private final UpdateTaskPort updateTaskPort;
    private final TaskListLock taskListLock;

    public TaskAssignedHandler(@Value("${task.list.name}") String taskListName,
                               UpdateTaskPort updateTaskPort,
                               TaskListLock taskListLock) {
        this.taskListName = taskListName;
        this.updateTaskPort = updateTaskPort;
        this.taskListLock = taskListLock;
    }

    @Override
    public Task handleTaskAssigned(TaskAssignedEvent taskAssignedEvent) throws TaskNotFoundException {

        taskListLock.lockTaskList(taskListName);
        Task task = updateTaskPort.updateTask(taskAssignedEvent.getTaskId(),
            new Task.TaskStatus(Task.Status.ASSIGNED), taskAssignedEvent.getServiceProvider(), Optional.empty());
        taskListLock.releaseTaskList(taskListName);

        return task;
    }
}
