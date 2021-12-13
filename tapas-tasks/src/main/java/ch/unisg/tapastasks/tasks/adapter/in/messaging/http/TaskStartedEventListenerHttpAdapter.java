package ch.unisg.tapastasks.tasks.adapter.in.messaging.http;

import ch.unisg.tapastasks.tasks.adapter.in.formats.TaskJsonPatchRepresentation;
import ch.unisg.tapastasks.tasks.application.handler.TaskExecutedHandler;
import ch.unisg.tapastasks.tasks.application.handler.TaskStartedHandler;
import ch.unisg.tapastasks.tasks.application.port.in.TaskStartedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskStartedEventHandler;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.Task.TaskId;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Listener for task started events. A task started event corresponds to a JSON Patch that attempts
 * to change the task's status to RUNNING and may also add/replace a service provider. This
 * implementation does not impose that a task started event includes the service provider (i.e.,
 * can be null).
 *
 * See also {@link TaskStartedEvent}, {@link Task}, and {@link TaskEventHttpDispatcher}.
 */
@Component
public class TaskStartedEventListenerHttpAdapter extends TaskEventListener {

    @Autowired
    TaskStartedHandler taskStartedHandler;

    public Task handleTaskEvent(String taskId, JsonNode payload) {
        TaskJsonPatchRepresentation representation = new TaskJsonPatchRepresentation(payload);
        Optional<Task.ServiceProvider> serviceProvider = representation.extractFirstServiceProviderChange();

        TaskStartedEvent taskStartedEvent = new TaskStartedEvent(new TaskId(taskId), serviceProvider);

        return taskStartedHandler.handleTaskStarted(taskStartedEvent);
    }
}
