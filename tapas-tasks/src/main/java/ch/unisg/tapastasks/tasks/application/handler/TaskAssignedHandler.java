package ch.unisg.tapastasks.tasks.application.handler;

import ch.unisg.tapastasks.tasks.application.port.in.TaskAssignedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskAssignedEventHandler;
import ch.unisg.tapascommon.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.application.port.out.AddTaskPort;
import ch.unisg.tapastasks.tasks.application.port.out.TaskListLock;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import ch.unisg.tapastasks.tasks.domain.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskAssignedHandler implements TaskAssignedEventHandler {

    @Autowired
    private AddTaskPort addTaskToRepositoryPort;

    @Autowired
    private TaskListLock taskListLock;

    @Override
    public Task handleTaskAssigned(TaskAssignedEvent taskAssignedEvent) throws TaskNotFoundException {
        TaskList taskList = TaskList.getTapasTaskList();
        taskListLock.lockTaskList(taskList.getTaskListName());

        var task = taskList.changeTaskStatusToAssigned(taskAssignedEvent.getTaskId(),
            taskAssignedEvent.getServiceProvider());

        addTaskToRepositoryPort.addTask(task);
        taskListLock.releaseTaskList(taskList.getTaskListName());

        return task;
    }
}
