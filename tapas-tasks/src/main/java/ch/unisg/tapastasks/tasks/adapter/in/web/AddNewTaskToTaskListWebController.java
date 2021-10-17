package ch.unisg.tapastasks.tasks.adapter.in.web;

import ch.unisg.tapastasks.tasks.adapter.in.formats.TaskJsonRepresentation;
import ch.unisg.tapastasks.tasks.application.port.in.AddNewTaskToTaskListCommand;
import ch.unisg.tapastasks.tasks.application.port.in.AddNewTaskToTaskListUseCase;
import ch.unisg.tapastasks.tasks.domain.Task;
import com.fasterxml.jackson.annotation.JsonAlias;
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
public class AddNewTaskToTaskListWebController {
    private final AddNewTaskToTaskListUseCase addNewTaskToTaskListUseCase;

    public AddNewTaskToTaskListWebController(AddNewTaskToTaskListUseCase addNewTaskToTaskListUseCase) {
        this.addNewTaskToTaskListUseCase = addNewTaskToTaskListUseCase;
    }

    @PostMapping(path = "/tasks/", consumes = {TaskJsonRepresentation.TASK_MEDIA_TYPE})
    public ResponseEntity<String> addNewTaskTaskToTaskList(@RequestBody TaskJsonRepresentation payload) {
        try {
            Task.TaskName taskName = new Task.TaskName(payload.getTaskName());
            Task.TaskType taskType = new Task.TaskType(payload.getTaskType());

            // If the created task is a delegated task, the representation contains a URI reference
            // to the original task
            Optional<Task.OriginalTaskUri> originalTaskUriOptional =
                (payload.getOriginalTaskUri() == null) ? Optional.empty()
                : Optional.of(new Task.OriginalTaskUri(payload.getOriginalTaskUri()));

            AddNewTaskToTaskListCommand command = new AddNewTaskToTaskListCommand(taskName, taskType,
                originalTaskUriOptional);

            Task createdTask = addNewTaskToTaskListUseCase.addNewTaskToTaskList(command);

            // When creating a task, the task's representation may include optional input data
            if (payload.getInputData() != null) {
                createdTask.setInputData(new Task.InputData(payload.getInputData()));
            }

            // Add the content type as a response header
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskJsonRepresentation.TASK_MEDIA_TYPE);

            return new ResponseEntity<>(TaskJsonRepresentation.serialize(createdTask), responseHeaders,
                HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
