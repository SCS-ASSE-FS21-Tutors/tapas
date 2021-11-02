package ch.unisg.assignment.assignment.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.unisg.assignment.assignment.application.port.in.DeleteTaskCommand;
import ch.unisg.assignment.assignment.application.port.in.DeleteTaskUseCase;
import ch.unisg.assignment.assignment.domain.Task;

@RestController
public class DeleteTaskController {
    private final DeleteTaskUseCase deleteTaskUseCase;

    public DeleteTaskController(DeleteTaskUseCase deleteTaskUseCase) {
        this.deleteTaskUseCase = deleteTaskUseCase;
    }

    /**
    *   Controller to delete a task
    *   @return 200 OK, 409 Conflict
    **/
    @DeleteMapping(path = "/task", consumes = {"application/task+json"})
    public ResponseEntity<Void> applyForTask(@RequestBody Task task) {

        DeleteTaskCommand command = new DeleteTaskCommand(task.getTaskID(), task.getTaskType());

        if (deleteTaskUseCase.deleteTask(command)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
