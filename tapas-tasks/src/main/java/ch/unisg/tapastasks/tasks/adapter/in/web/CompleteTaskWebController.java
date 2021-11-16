package ch.unisg.tapastasks.tasks.adapter.in.web;

import ch.unisg.tapastasks.tasks.adapter.in.formats.TaskJsonRepresentation;
import ch.unisg.tapastasks.tasks.application.port.in.CompleteTaskCommand;
import ch.unisg.tapastasks.tasks.application.port.in.CompleteTaskUseCase;
import ch.unisg.tapastasks.tasks.domain.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RestController
public class CompleteTaskWebController {
    private final CompleteTaskUseCase completeTaskUseCase;

    public CompleteTaskWebController(CompleteTaskUseCase completeTaskUseCase){
        this.completeTaskUseCase = completeTaskUseCase;
    }

    @GetMapping(path = "/tasks/completeTask/{taskId}")
    public ResponseEntity<String> completeTask (@PathVariable("taskId") String taskId){

        System.out.println("completeTask");
        System.out.println(taskId);

        String taskResult = "0";

        try {
            CompleteTaskCommand command = new CompleteTaskCommand(
                new Task.TaskId(taskId), new Task.OutputData(taskResult)
            );

            Task updateATask = completeTaskUseCase.completeTask(command);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskJsonRepresentation.MEDIA_TYPE);

            return new ResponseEntity<>(TaskJsonRepresentation.serialize(updateATask), responseHeaders, HttpStatus.ACCEPTED);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
