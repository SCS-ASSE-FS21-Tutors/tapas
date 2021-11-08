package ch.unisg.roster.roster.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.unisg.roster.roster.application.port.in.NewTaskCommand;
import ch.unisg.roster.roster.application.port.in.NewTaskUseCase;
import ch.unisg.roster.roster.domain.Task;

@RestController
public class NewTaskController {
    private final NewTaskUseCase newTaskUseCase;

    public NewTaskController(NewTaskUseCase newTaskUseCase) {
        this.newTaskUseCase = newTaskUseCase;
    }

    /**
    *   Controller which handles the new task event from the tasklist
    *   @return 201 Create or 409 Conflict
    **/
    @PostMapping(path = "/task", consumes = {"application/task+json"})
    public ResponseEntity<Void> newTaskController(@RequestBody Task task) {

            NewTaskCommand command = new NewTaskCommand(task.getTaskID(), task.getTaskType());

            boolean success = newTaskUseCase.addNewTaskToQueue(command);

            if (success) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);

    }
}
