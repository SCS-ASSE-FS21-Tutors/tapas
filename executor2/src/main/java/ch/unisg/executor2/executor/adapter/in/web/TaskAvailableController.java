package ch.unisg.executor2.executor.adapter.in.web;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ch.unisg.executorBase.executor.application.port.in.TaskAvailableCommand;
import ch.unisg.executorBase.executor.application.port.in.TaskAvailableUseCase;
import ch.unisg.executorBase.executor.domain.ExecutorType;

@RestController
public class TaskAvailableController {
    private final TaskAvailableUseCase taskAvailableUseCase;

    public TaskAvailableController(TaskAvailableUseCase taskAvailableUseCase) {
        this.taskAvailableUseCase = taskAvailableUseCase;
    }

    @GetMapping(path = "/newtask/{taskType}")
    public ResponseEntity<String> retrieveTaskFromTaskList(@PathVariable("taskType") String taskType) {

        if (ExecutorType.contains(taskType.toUpperCase())) {
            TaskAvailableCommand command = new TaskAvailableCommand(
                ExecutorType.valueOf(taskType.toUpperCase()));
            CompletableFuture.runAsync(() -> taskAvailableUseCase.newTaskAvailable(command));
        }

        return new ResponseEntity<>("OK", new HttpHeaders(), HttpStatus.OK);
    }
}
