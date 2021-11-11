package ch.unisg.tapastasks.tasks.application.handler;

import ch.unisg.tapastasks.tasks.application.port.in.TaskStartedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskStartedEventHandler;
import ch.unisg.tapascommon.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import ch.unisg.tapastasks.tasks.domain.TaskNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class TaskStartedHandler implements TaskStartedEventHandler {

    @Override
    public Task handleTaskStarted(TaskStartedEvent taskStartedEvent) throws TaskNotFoundException {
        TaskList taskList = TaskList.getTapasTaskList();
        return taskList.changeTaskStatusToRunning(taskStartedEvent.getTaskId(),
            taskStartedEvent.getServiceProvider());
    }
}
