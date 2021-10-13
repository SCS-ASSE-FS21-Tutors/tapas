package ch.unisg.tapastasks.tasks.adapter.in.web;

import ch.unisg.tapastasks.tasks.application.port.in.RetrieveTaskFromTaskListQuery;
import ch.unisg.tapastasks.tasks.application.port.in.RetrieveTaskFromTaskListUseCase;
import ch.unisg.tapastasks.tasks.domain.Task;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class RetrieveTaskFromTaskListWebController {
    private final RetrieveTaskFromTaskListUseCase retrieveTaskFromTaskListUseCase;

    public RetrieveTaskFromTaskListWebController(RetrieveTaskFromTaskListUseCase retrieveTaskFromTaskListUseCase) {
        this.retrieveTaskFromTaskListUseCase = retrieveTaskFromTaskListUseCase;
    }

    @GetMapping(path = "/tasks/{taskId}")
    public ResponseEntity<String> retrieveTaskFromTaskList(@PathVariable("taskId") String taskId) {

        RetrieveTaskFromTaskListQuery command = new RetrieveTaskFromTaskListQuery(new Task.TaskId(taskId));
        Optional<Task> updatedTaskOpt = retrieveTaskFromTaskListUseCase.retrieveTaskFromTaskList(command);

        // Check if the task with the given identifier exists
        if (updatedTaskOpt.isEmpty()) {
            // If not, through a 404 Not Found status code
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // Add the content type as a response header
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskMediaType.TASK_MEDIA_TYPE);

        return new ResponseEntity<>(TaskMediaType.serialize(updatedTaskOpt.get()), responseHeaders,
                HttpStatus.OK);
    }
}
