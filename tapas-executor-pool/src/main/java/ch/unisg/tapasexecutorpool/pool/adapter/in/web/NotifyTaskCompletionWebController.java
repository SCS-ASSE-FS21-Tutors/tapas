package ch.unisg.tapasexecutorpool.pool.adapter.in.web;

import ch.unisg.tapasexecutorpool.common.formats.TaskJsonRepresentation;
import ch.unisg.tapasexecutorpool.pool.application.port.in.NotifyTaskCompletionCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.NotifyTaskCompletionUseCase;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RestController
public class NotifyTaskCompletionWebController {
    private final NotifyTaskCompletionUseCase notifyTaskCompletionUseCase;

    public NotifyTaskCompletionWebController(NotifyTaskCompletionUseCase notifyTaskCompletionUseCase) {
        this.notifyTaskCompletionUseCase = notifyTaskCompletionUseCase;
    }

    @PutMapping(path = "/completion", consumes = TaskJsonRepresentation.MEDIA_TYPE)
    public ResponseEntity<String> notifyTaskCompletion(@RequestBody TaskJsonRepresentation payload) {
        try {
            Task task = TaskJsonRepresentation.toTask(payload);
            String taskId = task.getTaskId().getValue();
            String outputData = task.getOutputData().getValue();

            NotifyTaskCompletionCommand command = new NotifyTaskCompletionCommand(
                    taskId, outputData
            );

            notifyTaskCompletionUseCase.notifyTaskCompletion(command);

            // Add the content type as a response header
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, ExecutorMediaType.EXECUTOR_MEDIA_TYPE);

            return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
