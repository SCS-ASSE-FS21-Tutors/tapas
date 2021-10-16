package ch.unisg.tapastasks.tasks.adapter.in.messaging.http;

import ch.unisg.tapastasks.tasks.adapter.in.common.TaskMediaType;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;


/**
 * This REST Controller handles HTTP PATCH requests for updating the representational state of Task
 * resources. The payload of a request uses the JSON PATCH format and media type. Each request to
 * update the representational state of a Task resource can correspond to a domain event, but the
 * domain event can only be determined by inspecting the requested patch (e.g., a request to change
 * the state of a Task from RUNNING to EXECUTED). This class is responsible to inspect requested
 * patches and to route events to appropriate listeners.
 */
@RestController
public class TaskEventHttpDispatcher {
    private final static String TASKID_PATH_PARAMETER = "taskId";
    private final static String JSON_PATCH_MEDIA_TYPE = "application/json-patch+json";

    @PatchMapping(path = "/tasks/{" + TASKID_PATH_PARAMETER + "}", consumes = {JSON_PATCH_MEDIA_TYPE})
    public ResponseEntity<String> dispatchTaskEvents(@PathVariable(TASKID_PATH_PARAMETER) String taskId,
                                                     @RequestBody JsonNode payload) {
        try {
            // Throw an exception if the JSON Patch format is invalid. This call is only used to
            // validate the JSON PATCH syntax.
            JsonPatch.fromJson(payload);

            // Check for known events and route the events to appropriate listeners
            if (payload.isArray()) {
                for (JsonNode node : payload) {
                    if (node.isObject() && node.get("op") != null && node.get("path") != null
                            && node.get("value") != null) {

                        String op = node.get("op").asText();
                        String path = node.get("path").asText();
                        String value = node.get("value").asText();

                        TaskEventListener listener = null;

                        // Route events related to task states
                        if (op.equalsIgnoreCase("replace") && path.equals("/taskState")) {
                            switch (Task.State.valueOf(value.toUpperCase())) {
                                case ASSIGNED:
                                    listener = new TaskAssignedEventListenerHttpAdapter();
                                    break;
                                case RUNNING:
                                    listener = new TaskStartedEventListenerHttpAdapter();
                                    break;
                                case EXECUTED:
                                    listener = new TaskExecutedEventListenerHttpAdapter();
                                    break;
                            }
                        }

                        if (listener == null) {
                            // The HTTP PATCH request is valid, but the patch does not match any
                            // known event
                            // TODO: throw custom exception?
                            throw new RuntimeException("Unknown event for patch: "
                                + payload.toPrettyString());
                        }

                        Task task = listener.handleTaskEvent(taskId, payload);

                        // Add the content type as a response header
                        HttpHeaders responseHeaders = new HttpHeaders();
                        responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskMediaType.TASK_MEDIA_TYPE);

                        return new ResponseEntity<>(TaskMediaType.serialize(task), responseHeaders,
                            HttpStatus.OK);
                    }
                }
            }
        } catch (TaskNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IOException | RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
