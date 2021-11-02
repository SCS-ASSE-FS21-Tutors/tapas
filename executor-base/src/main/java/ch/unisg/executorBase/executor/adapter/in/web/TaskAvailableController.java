package ch.unisg.executorbase.executor.adapter.in.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ch.unisg.executorbase.executor.application.port.in.TaskAvailableCommand;
import ch.unisg.executorbase.executor.application.port.in.TaskAvailableUseCase;
import ch.unisg.executorbase.executor.domain.ExecutorType;

@RestController
public class TaskAvailableController {
    private final TaskAvailableUseCase taskAvailableUseCase;

    public TaskAvailableController(TaskAvailableUseCase taskAvailableUseCase) {
        this.taskAvailableUseCase = taskAvailableUseCase;
    }

    /**
    *   Controller for notification about new events.
    *   @return 200 OK
    **/
    @GetMapping(path = "/newtask/{taskType}", consumes = { "application/json" })
    public ResponseEntity<String> retrieveTaskFromTaskList(@PathVariable("taskType") String taskType) {

        if (ExecutorType.contains(taskType.toUpperCase())) {
            TaskAvailableCommand command = new TaskAvailableCommand(
                ExecutorType.valueOf(taskType.toUpperCase()));
            taskAvailableUseCase.newTaskAvailable(command);
        }

        // Add the content type as a response header
        HttpHeaders responseHeaders = new HttpHeaders();

        return new ResponseEntity<>("OK", responseHeaders, HttpStatus.OK);
    }
}
