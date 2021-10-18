package ch.unisg.tapasexecutorpool.pool.adapter.in.web;

import ch.unisg.tapasexecutorpool.pool.application.port.in.NotifyTaskCompletionCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.NotifyTaskCompletionUseCase;
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

    @PutMapping(path = "/completion/")
    public ResponseEntity<String> notifyTaskCompletion(@RequestParam String taskId) {
        try {
            NotifyTaskCompletionCommand command = new NotifyTaskCompletionCommand(
                    taskId
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