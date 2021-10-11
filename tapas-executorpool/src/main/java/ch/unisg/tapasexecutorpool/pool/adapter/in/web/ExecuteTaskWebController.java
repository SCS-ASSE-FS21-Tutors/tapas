package ch.unisg.tapasexecutorpool.pool.adapter.in.web;

import ch.unisg.tapasexecutorpool.pool.application.port.in.ExecuteTaskCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.ExecuteTaskUseCase;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RestController
public class ExecuteTaskWebController {

    private final ExecuteTaskUseCase executeTaskUseCase;

    public ExecuteTaskWebController(ExecuteTaskUseCase executeTaskUseCase) {
        this.executeTaskUseCase = executeTaskUseCase;
    }

    @PostMapping(path = "/executor-pool/execute-task/", consumes = {TaskMediaType.TASK_MEDIA_TYPE})
    public ResponseEntity<String> executeTask(@RequestBody Task task) {
        try {
            var command = new ExecuteTaskCommand(task);

            Task newTask = executeTaskUseCase.executeTask(command);

            // Add the content type as a response header
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskMediaType.TASK_MEDIA_TYPE);

            return new ResponseEntity<>(TaskMediaType.serialize(newTask), responseHeaders, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
