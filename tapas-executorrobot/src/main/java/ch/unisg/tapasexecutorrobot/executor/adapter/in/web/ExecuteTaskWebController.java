package ch.unisg.tapasexecutorrobot.executor.adapter.in.web;

import ch.unisg.tapascommon.tasks.adapter.in.formats.TaskJsonRepresentation;
import ch.unisg.tapasexecutorrobot.executor.application.port.in.ExecuteTaskCommand;
import ch.unisg.tapasexecutorrobot.executor.application.port.in.ExecuteTaskUseCase;
import ch.unisg.tapascommon.tasks.domain.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @PostMapping(path = "/executor-robot/execute-task/", consumes = {TaskJsonRepresentation.MEDIA_TYPE})
    public ResponseEntity<String> executeTask(@RequestBody TaskJsonRepresentation task) {
        try {
            var command = new ExecuteTaskCommand(task.deserialize());
            var newTask = executeTaskUseCase.executeTask(command);
            var responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskJsonRepresentation.MEDIA_TYPE);
            return new ResponseEntity<>(TaskJsonRepresentation.serialize(newTask), responseHeaders, HttpStatus.CREATED);
        } catch (ConstraintViolationException | JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
