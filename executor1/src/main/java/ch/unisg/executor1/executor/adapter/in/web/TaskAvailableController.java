package ch.unisg.executor1.executor.adapter.in.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ch.unisg.executor1.executor.application.port.in.TaskAvailableCommand;
import ch.unisg.executor1.executor.application.port.in.TaskAvailableUseCase;

@RestController
public class TaskAvailableController {
    private final TaskAvailableUseCase taskAvailableUseCase;

    public TaskAvailableController(TaskAvailableUseCase taskAvailableUseCase) {
        this.taskAvailableUseCase = taskAvailableUseCase;
    }

    @GetMapping(path = "/newtask/{taskType}")
    public ResponseEntity<String> retrieveTaskFromTaskList(@PathVariable("taskType") String taskType) {
        TaskAvailableCommand command = new TaskAvailableCommand(taskType);
        
        taskAvailableUseCase.newTaskAvailable(command);
        
        // Add the content type as a response header
        HttpHeaders responseHeaders = new HttpHeaders();

        return new ResponseEntity<>("OK", responseHeaders, HttpStatus.OK);
    }
}
