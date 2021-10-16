package ch.unisg.tapastasks.tasks.adapter.in.messaging.http;

import ch.unisg.tapastasks.tasks.adapter.in.formats.TaskJsonPatchRepresentation;
import ch.unisg.tapastasks.tasks.application.handler.TaskExecutedHandler;
import ch.unisg.tapastasks.tasks.application.port.in.TaskExecutedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskExecutedEventHandler;
import ch.unisg.tapastasks.tasks.domain.Task;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public class TaskExecutedEventListenerHttpAdapter extends TaskEventListener {

    public Task handleTaskEvent(String taskId, JsonNode payload) {
        TaskJsonPatchRepresentation representation = new TaskJsonPatchRepresentation(payload);

        Optional<Task.ServiceProvider> serviceProvider = representation.extractServiceProvider();
        Optional<Task.OutputData> outputData = representation.extractOutput();

        TaskExecutedEvent taskExecutedEvent = new TaskExecutedEvent(new Task.TaskId(taskId),
            serviceProvider, outputData);
        TaskExecutedEventHandler taskExecutedEventHandler = new TaskExecutedHandler();

        return taskExecutedEventHandler.handleTaskExecuted(taskExecutedEvent);
    }
}
