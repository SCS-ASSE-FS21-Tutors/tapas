package ch.unisg.tapastasks.tasks.adapter.in.web;

import ch.unisg.tapastasks.tasks.adapter.TaskMediaType;
import ch.unisg.tapastasks.tasks.application.port.in.AddNewTaskToTaskListCommand;
import ch.unisg.tapastasks.tasks.application.port.in.AddNewTaskToTaskListUseCase;
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
public class AddNewTaskToTaskListWebController {
    private final AddNewTaskToTaskListUseCase addNewTaskToTaskListUseCase;

    public AddNewTaskToTaskListWebController(AddNewTaskToTaskListUseCase addNewTaskToTaskListUseCase) {
        this.addNewTaskToTaskListUseCase = addNewTaskToTaskListUseCase;
    }

    @PostMapping(path = "/tasks/", consumes = {TaskMediaType.TASK_MEDIA_TYPE})
    public ResponseEntity<String> addNewTaskTaskToTaskList(@RequestBody Task task) {
        try {
            AddNewTaskToTaskListCommand command = new AddNewTaskToTaskListCommand(
                    task.getTaskName(), task.getTaskType()
            );

            Task newTask = addNewTaskToTaskListUseCase.addNewTaskToTaskList(command);

            // Add the content type as a response header
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskMediaType.TASK_MEDIA_TYPE);

            return new ResponseEntity<>(TaskMediaType.serialize(newTask), responseHeaders, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
