package ch.unisg.tapasexecutorpool.executorpool.adapter.in.web;

import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AssignTaskToRosterCommand;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AssignTaskToRosterUseCase;
import ch.unisg.tapasexecutorpool.executorpool.domain.Task;
import ch.unisg.tapasexecutorpool.executorpool.domain.TaskAssignmentReply;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class AssignTaskToRosterWebController {
    private final AssignTaskToRosterUseCase assignTaskToRosterUseCase;

    public AssignTaskToRosterWebController(AssignTaskToRosterUseCase assignTaskToRosterUseCase) {
        this.assignTaskToRosterUseCase = assignTaskToRosterUseCase;
    }

    @PostMapping(path = "/roster/newtask/", consumes = {TaskMediaType.TASK_MEDIA_TYPE})
    public ResponseEntity<String> assignTaskToRoster(@RequestBody Task task) {
        try {
            AssignTaskToRosterCommand command = new AssignTaskToRosterCommand(task);

            Optional<TaskAssignmentReply> reply = assignTaskToRosterUseCase.assignTaskToRoster(command);

            if (reply.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }

            // Add the content type as a response header
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskAssignmentMediaType.MEDIA_TYPE);

            return new ResponseEntity<>(TaskAssignmentMediaType.serialize(reply.get()), responseHeaders, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
