package ch.unisg.tapasexecutorpool.pool.adapter.in.web;

import ch.unisg.tapasexecutorpool.common.formats.ExecutorJsonRepresentation;
import ch.unisg.tapasexecutorpool.common.formats.TaskJsonRepresentation;
import ch.unisg.tapasexecutorpool.pool.application.port.in.NotifyTaskCompletionCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.NotifyTaskCompletionUseCase;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RestController
@Log4j2
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
            log.info("Received task completed notification for task {}, output: {}", taskId, outputData);

            NotifyTaskCompletionCommand command = new NotifyTaskCompletionCommand(
                    taskId, outputData
            );

            notifyTaskCompletionUseCase.notifyTaskCompletion(command);

            // Respond with HTTP OK code
            HttpHeaders responseHeaders = new HttpHeaders();
            return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
