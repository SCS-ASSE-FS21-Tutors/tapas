package ch.unisg.tapasroster.roster.adapter.in.web;

import ch.unisg.tapasroster.roster.application.port.in.AddTaskToTaskQueueCommand;
import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToExecutorInExecutorPoolCommand;
import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToExecutorInExecutorPoolUseCase;
import ch.unisg.tapasroster.roster.domain.Task;
import org.json.JSONException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RestController
public class AssignTaskToExecutorInExecutorPoolWebAdapter {
    private final AssignTaskToExecutorInExecutorPoolUseCase assignTaskToExecutorInExecutorPoolUseCase;

    public AssignTaskToExecutorInExecutorPoolWebAdapter(AssignTaskToExecutorInExecutorPoolUseCase assignTaskToExecutorInExecutorPoolUseCase) {
        this.assignTaskToExecutorInExecutorPoolUseCase = assignTaskToExecutorInExecutorPoolUseCase;
    }

    @PostMapping(path = "/roster/newtask", consumes = {TaskMediaType.TASK_MEDIA_TYPE})
    public ResponseEntity<String> addNewTaskTaskToTaskList(@RequestBody Task task) {
        try {
            AssignTaskToExecutorInExecutorPoolCommand command = new AssignTaskToExecutorInExecutorPoolCommand(
                    task.getTaskId(), task.getTaskType()
            );

            //Not clear yet what it is supposed to return
            Task newTask = assignTaskToExecutorInExecutorPoolUseCase.assignTaskToExecutor(command);

            // Add the content type as a response header
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskMediaType.TASK_MEDIA_TYPE);

            return new ResponseEntity<>(TaskMediaType.serialize(newTask), responseHeaders, HttpStatus.CREATED);
        } catch (ConstraintViolationException | JSONException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}