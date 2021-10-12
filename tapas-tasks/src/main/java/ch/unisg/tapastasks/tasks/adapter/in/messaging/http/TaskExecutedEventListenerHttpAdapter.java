package ch.unisg.tapastasks.tasks.adapter.in.messaging.http;

import ch.unisg.tapastasks.tasks.application.handler.TaskExecutedHandler;
import ch.unisg.tapastasks.tasks.application.port.in.TaskExecutedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskExecutedEventHandler;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.Task.TaskId;
import com.fasterxml.jackson.databind.JsonNode;

public class TaskExecutedEventListenerHttpAdapter extends TaskEventListener {

    public Task handleTaskEvent(String taskId, JsonNode payload) {
        TaskExecutedEvent taskExecutedEvent = new TaskExecutedEvent(new TaskId(taskId));
        TaskExecutedEventHandler taskExecutedEventHandler = new TaskExecutedHandler();

        return taskExecutedEventHandler.handleTaskExecuted(taskExecutedEvent);
    }
}
