package ch.unisg.tapastasks.tasks.adapter.in.web;

import ch.unisg.tapastasks.tasks.application.port.in.TaskAssignedCommand;
import ch.unisg.tapastasks.tasks.application.port.in.TaskAssignedUseCase;
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
public class TaskAssignedWebController {
    private final TaskAssignedUseCase taskAssignedUseCase;

    public TaskAssignedWebController(TaskAssignedUseCase taskAssignedUseCase){
        this.taskAssignedUseCase = taskAssignedUseCase;
    }

    @PostMapping(path="/tasks/assignTask", consumes= {TaskMediaType.TASK_MEDIA_TYPE})
    public ResponseEntity<String> assignTask(@RequestBody Task task){
        try{
            TaskAssignedCommand command = new TaskAssignedCommand(
                task.getTaskId()
            );

            Task updateATask = taskAssignedUseCase.assignTask(command);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskMediaType.TASK_MEDIA_TYPE);

            return new ResponseEntity<>(TaskMediaType.serialize(updateATask), responseHeaders, HttpStatus.ACCEPTED);
        } catch (ConstraintViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
