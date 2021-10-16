package ch.unisg.tapasroster.roster.adapter.in.web;

import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToRosterCommand;
import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToRosterUseCase;
import ch.unisg.tapasroster.roster.application.port.in.TaskExecutionFinishedCommand;
import ch.unisg.tapasroster.roster.application.port.in.TaskExecutionFinishedUseCase;
import ch.unisg.tapasroster.roster.domain.Task;
import ch.unisg.tapasroster.roster.domain.TaskAssignmentReply;
import ch.unisg.tapasroster.roster.domain.TaskFinishedReply;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@RestController
public class TaskExecutionFinishedWebController {
    private final TaskExecutionFinishedUseCase taskExecutionFinishedUseCase;

    public TaskExecutionFinishedWebController(TaskExecutionFinishedUseCase taskExecutionFinishedUseCase) {
        this.taskExecutionFinishedUseCase = taskExecutionFinishedUseCase;
    }

    @PostMapping(path = "/roster/taskfinished", consumes = {TaskAssignmentMediaType.MEDIA_TYPE})
    public ResponseEntity<String>  notifyRosterTaskExecutionFinished(@RequestBody TaskFinishedReply reply) {
        try {
            System.out.println(reply);

            TaskExecutionFinishedCommand command = new TaskExecutionFinishedCommand(reply);
            taskExecutionFinishedUseCase.notifyRosterTaskExecutionFinished(command);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
