package ch.unisg.roster.roster.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.unisg.roster.roster.application.port.in.TaskCompletedCommand;
import ch.unisg.roster.roster.application.port.in.TaskCompletedUseCase;
import ch.unisg.roster.roster.domain.Task;

@RestController
public class TaskCompletedController {

    private final TaskCompletedUseCase taskCompletedUseCase;

    public TaskCompletedController(TaskCompletedUseCase taskCompletedUseCase) {
        this.taskCompletedUseCase = taskCompletedUseCase;
    }

    /**
    *   Controller which handles the task completed event from executors
    *   @return 200 OK
    **/
    @PostMapping(path = "/task/completed", consumes = {"application/json"})
    public ResponseEntity<Void> addNewTaskTaskToTaskList(@RequestBody Task task) {

        TaskCompletedCommand command = new TaskCompletedCommand(task.getTaskID(),
            task.getStatus(), task.getResult());

        taskCompletedUseCase.taskCompleted(command);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
