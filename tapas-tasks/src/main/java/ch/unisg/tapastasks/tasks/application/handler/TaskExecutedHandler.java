package ch.unisg.tapastasks.tasks.application.handler;

import ch.unisg.tapastasks.tasks.application.port.in.TaskExecutedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskExecutedEventHandler;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import org.springframework.stereotype.Component;

@Component
public class TaskExecutedHandler implements TaskExecutedEventHandler {

    @Override
    public boolean handleTaskExecuted(TaskExecutedEvent taskExecutedEvent) {
        TaskList taskList = TaskList.getTapasTaskList();
        return taskList.changeTaskStatusToExecuted(taskExecutedEvent.getTaskId());
    }
}