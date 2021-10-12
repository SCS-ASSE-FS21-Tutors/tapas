package ch.unisg.tapastasks.tasks.adapter.in.messaging.http;

import ch.unisg.tapastasks.tasks.application.handler.TaskStartedHandler;
import ch.unisg.tapastasks.tasks.application.port.in.TaskStartedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskStartedEventHandler;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.Task.TaskId;
import com.fasterxml.jackson.databind.JsonNode;

public class TaskStartedEventListenerHttpAdapter extends TaskEventListener {

    public Task handleTaskEvent(String taskId, JsonNode payload) {
        TaskStartedEvent taskStartedEvent = new TaskStartedEvent(new TaskId(taskId));
        TaskStartedEventHandler taskStartedEventHandler = new TaskStartedHandler();

        return taskStartedEventHandler.handleTaskStarted(taskStartedEvent);
    }
}
