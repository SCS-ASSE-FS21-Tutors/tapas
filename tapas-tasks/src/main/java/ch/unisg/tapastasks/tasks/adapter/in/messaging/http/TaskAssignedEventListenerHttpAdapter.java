package ch.unisg.tapastasks.tasks.adapter.in.messaging.http;

import ch.unisg.tapastasks.tasks.adapter.in.formats.TaskJsonPatchRepresentation;
import ch.unisg.tapastasks.tasks.application.handler.TaskAssignedHandler;
import ch.unisg.tapastasks.tasks.application.port.in.TaskAssignedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskAssignedEventHandler;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.Task.TaskId;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public class TaskAssignedEventListenerHttpAdapter extends TaskEventListener {

    public Task handleTaskEvent(String taskId, JsonNode payload) {
        TaskJsonPatchRepresentation representation = new TaskJsonPatchRepresentation(payload);
        Optional<Task.ServiceProvider> serviceProvider = representation.extractFirstServiceProviderChange();

        TaskAssignedEvent taskAssignedEvent = new TaskAssignedEvent(new TaskId(taskId), serviceProvider);
        TaskAssignedEventHandler taskAssignedEventHandler = new TaskAssignedHandler();

        return taskAssignedEventHandler.handleTaskAssigned(taskAssignedEvent);
    }
}
