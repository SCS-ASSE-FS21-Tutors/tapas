package ch.unisg.tapasroster.roster.adapter.in.web;

import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToExecutorCommand;
import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToExecutorUseCase;
import ch.unisg.tapasroster.roster.domain.Task;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RestController
public class AssignTaskToExecutorWebAdapter {
    private final AssignTaskToExecutorUseCase assignTaskToExecutorUseCase;

    public AssignTaskToExecutorWebAdapter(AssignTaskToExecutorUseCase assignTaskToExecutorUseCase) {
        this.assignTaskToExecutorUseCase = assignTaskToExecutorUseCase;
    }

    @PostMapping(path = "/roster/newtask", consumes = {TaskMediaType.TASK_MEDIA_TYPE})
    public ResponseEntity<String> addNewTaskTaskToTaskList(@RequestBody Task task) {
        try {
            AssignTaskToExecutorCommand command = new AssignTaskToExecutorCommand(
                    task.getTaskId(), task.getTaskType(), task.getTaskUri()
            );

            // Assign an Executor for the incoming Task
            boolean newTaskAssigned = assignTaskToExecutorUseCase.assignTaskToExecutor(command);

            // Add the content type as a response header
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskMediaType.TASK_MEDIA_TYPE);

            JSONObject response = new JSONObject();
            response.put("Task successfully assigned", newTaskAssigned);

            // Send back whether assignment was successful or not
            return new ResponseEntity<>(response.toString(), responseHeaders, HttpStatus.CREATED);
        } catch (ConstraintViolationException | JSONException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}