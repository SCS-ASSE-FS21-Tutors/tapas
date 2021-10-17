package ch.unisg.assignment.assignment.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.unisg.assignment.assignment.application.port.in.NewTaskCommand;
import ch.unisg.assignment.assignment.application.port.in.NewTaskUseCase;
import ch.unisg.assignment.assignment.domain.Task;

@RestController
public class NewTaskController {
    private final NewTaskUseCase newTaskUseCase;

    public NewTaskController(NewTaskUseCase newTaskUseCase) {
        this.newTaskUseCase = newTaskUseCase;
    }

    @PostMapping(path = "/task", consumes = {"application/json"})
    public ResponseEntity<Void> newTaskController(@RequestBody Task task) {

            NewTaskCommand command = new NewTaskCommand(task.getTaskID(), task.getTaskType());

            boolean success = newTaskUseCase.addNewTaskToQueue(command);

            if (success) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);

    }
}
