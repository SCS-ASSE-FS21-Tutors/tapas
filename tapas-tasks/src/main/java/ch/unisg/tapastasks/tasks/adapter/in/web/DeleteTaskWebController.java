package ch.unisg.tapastasks.tasks.adapter.in.web;


import ch.unisg.tapastasks.tasks.adapter.in.formats.TaskJsonRepresentation;
import ch.unisg.tapastasks.tasks.application.port.in.DeleteTaskCommand;
import ch.unisg.tapastasks.tasks.application.port.in.DeleteTaskUseCase;
import ch.unisg.tapastasks.tasks.domain.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@RestController
public class DeleteTaskWebController {
    private final DeleteTaskUseCase deleteClassUseCase;

    public DeleteTaskWebController(DeleteTaskUseCase deleteClassUseCase){
        this.deleteClassUseCase = deleteClassUseCase;
    }

    @PostMapping(path="/tasks/deleteTask", consumes = {TaskJsonRepresentation.MEDIA_TYPE})
    public ResponseEntity<String> deleteTask (@RequestBody Task task){
        try {
            DeleteTaskCommand command = new DeleteTaskCommand(task.getTaskId(), task.getOriginalTaskUri());

            Optional<Task> deleteATask = deleteClassUseCase.deleteTask(command);

            // Check if the task with the given identifier exists
            if (deleteATask.isEmpty()) {
                // If not, through a 404 Not Found status code
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskJsonRepresentation.MEDIA_TYPE);


            return new ResponseEntity<>(TaskJsonRepresentation.serialize(deleteATask.get()), responseHeaders, HttpStatus.ACCEPTED);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}