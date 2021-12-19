package ch.unisg.tapastasks.tasks.adapter.in.web;

import ch.unisg.tapastasks.tasks.adapter.in.formats.NewTaskJsonRepresentation;
import ch.unisg.tapastasks.tasks.adapter.in.formats.TaskJsonRepresentation;
import ch.unisg.tapastasks.tasks.application.port.in.AddNewTaskToTaskListCommand;
import ch.unisg.tapastasks.tasks.application.port.in.AddNewTaskToTaskListUseCase;
import ch.unisg.tapastasks.tasks.domain.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

/**
 * Controller that handles HTTP requests for creating new tasks. This controller implements the
 * {@link AddNewTaskToTaskListUseCase} use case using the {@link AddNewTaskToTaskListCommand}.
 *
 * A new task is created via an HTTP POST request to the /tasks/ endpoint. The body of the request
 * contains a JSON-based representation with the "application/task+json" media type defined for this
 * project. This custom media type allows to capture the semantics of our JSON representations for
 * tasks.
 *
 * If the request is successful, the controller returns an HTTP 201 Created status code and a
 * representation of the created task with Content-Type "application/task+json". The HTTP response
 * also include a Location header field that points to the URI of the created task.
 */
@RestController
@Log4j2
public class AddNewTaskToTaskListWebController {
    private final AddNewTaskToTaskListUseCase addNewTaskToTaskListUseCase;

    // Used to retrieve properties from application.properties
    @Autowired
    private Environment environment;

    public AddNewTaskToTaskListWebController(AddNewTaskToTaskListUseCase addNewTaskToTaskListUseCase) {
        this.addNewTaskToTaskListUseCase = addNewTaskToTaskListUseCase;
    }

    @PostMapping(path = "/tasks/", consumes = {NewTaskJsonRepresentation.MEDIA_TYPE})
    public ResponseEntity<String> addNewTaskTaskToTaskList(@RequestBody NewTaskJsonRepresentation payload) {
        try {
            Task.TaskName taskName = new Task.TaskName(payload.getTaskName());
            Task.TaskType taskType = new Task.TaskType(payload.getTaskType());
            Task.InputData inputData = new Task.InputData(payload.getInputData());

            log.info("Add new task request received for task {}, {}, {}",
                taskName.getValue(), taskType.getValue(), inputData.getValue());

            AddNewTaskToTaskListCommand command = new AddNewTaskToTaskListCommand(taskName, taskType, inputData);

            Task createdTask = addNewTaskToTaskListUseCase.addNewTaskToTaskList(command);

            // Add the content type as a response header
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskJsonRepresentation.MEDIA_TYPE);
            // from the application.properties file
            responseHeaders.add(HttpHeaders.LOCATION, environment.getProperty("baseuri")
                + "tasks/" + createdTask.getTaskId().getValue());

            return new ResponseEntity<>(TaskJsonRepresentation.serialize(createdTask), responseHeaders,
                HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
