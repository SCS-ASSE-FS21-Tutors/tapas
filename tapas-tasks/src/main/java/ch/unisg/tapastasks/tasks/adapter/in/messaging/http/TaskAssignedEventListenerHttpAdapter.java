package ch.unisg.tapastasks.tasks.adapter.in.messaging.http;

import ch.unisg.tapastasks.tasks.application.handler.TaskAssignedHandler;
import ch.unisg.tapastasks.tasks.application.port.in.TaskAssignedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskAssignedEventHandler;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.Task.TaskId;
import com.fasterxml.jackson.databind.JsonNode;

public class TaskAssignedEventListenerHttpAdapter extends TaskEventListener {

    public Task handleTaskEvent(String taskId, JsonNode payload) {
        TaskAssignedEvent taskAssignedEvent = new TaskAssignedEvent(new TaskId(taskId));
        TaskAssignedEventHandler taskAssignedEventHandler = new TaskAssignedHandler();

        return taskAssignedEventHandler.handleTaskAssigned(taskAssignedEvent);
    }
}
