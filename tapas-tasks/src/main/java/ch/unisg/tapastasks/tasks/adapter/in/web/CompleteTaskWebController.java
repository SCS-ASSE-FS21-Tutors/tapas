package ch.unisg.tapastasks.tasks.adapter.in.web;

import ch.unisg.tapastasks.tasks.application.port.in.CompleteTaskCommand;
import ch.unisg.tapastasks.tasks.application.port.in.CompleteTaskUseCase;
import ch.unisg.tapastasks.tasks.domain.Task;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RestController
public class CompleteTaskWebController {
    private final CompleteTaskUseCase completeTaskUseCase;

    public CompleteTaskWebController(CompleteTaskUseCase completeTaskUseCase){
        this.completeTaskUseCase = completeTaskUseCase;
    }

    @PostMapping(path = "/tasks/completeTask", consumes = {TaskMediaType.TASK_MEDIA_TYPE})
    public ResponseEntity<String> completeTask (@RequestBody Task task){
        try {
            CompleteTaskCommand command = new CompleteTaskCommand(
                task.getTaskId(), task.getTaskResult()
            );

            Task updateATask = completeTaskUseCase.completeTask(command);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskMediaType.TASK_MEDIA_TYPE);

            return new ResponseEntity<>(TaskMediaType.serialize(updateATask), responseHeaders, HttpStatus.ACCEPTED);
        } catch(ConstraintViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
