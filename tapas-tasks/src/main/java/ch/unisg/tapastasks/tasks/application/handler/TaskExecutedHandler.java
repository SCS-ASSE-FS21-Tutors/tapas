package ch.unisg.tapastasks.tasks.application.handler;

import ch.unisg.tapastasks.tasks.application.port.in.TaskExecutedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskExecutedEventHandler;
import ch.unisg.tapascommon.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.application.port.out.AddTaskPort;
import ch.unisg.tapastasks.tasks.application.port.out.TaskListLock;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import ch.unisg.tapastasks.tasks.domain.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskExecutedHandler implements TaskExecutedEventHandler {

    @Autowired
    private AddTaskPort addTaskToRepositoryPort;

    @Autowired
    private TaskListLock taskListLock;

    @Override
    public Task handleTaskExecuted(TaskExecutedEvent taskExecutedEvent) throws TaskNotFoundException {
        TaskList taskList = TaskList.getTapasTaskList();
        taskListLock.lockTaskList(taskList.getTaskListName());

        var task = taskList.changeTaskStatusToExecuted(taskExecutedEvent.getTaskId(),
            taskExecutedEvent.getServiceProvider(), taskExecutedEvent.getOutputData());

        addTaskToRepositoryPort.addTask(task);
        taskListLock.releaseTaskList(taskList.getTaskListName());

        return task;
    }
}
