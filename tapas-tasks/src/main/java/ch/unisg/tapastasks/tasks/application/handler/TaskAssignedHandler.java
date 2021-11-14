package ch.unisg.tapastasks.tasks.application.handler;

import ch.unisg.tapastasks.tasks.application.port.in.TaskAssignedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskAssignedEventHandler;
import ch.unisg.tapascommon.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import ch.unisg.tapastasks.tasks.domain.TaskNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class TaskAssignedHandler implements TaskAssignedEventHandler {

    @Override
    public Task handleTaskAssigned(TaskAssignedEvent taskAssignedEvent) throws TaskNotFoundException {
        TaskList taskList = TaskList.getTapasTaskList();
        return taskList.changeTaskStatusToAssigned(taskAssignedEvent.getTaskId(),
            taskAssignedEvent.getServiceProvider());
    }
}
