package ch.unisg.tapasroster.roster.adapter.in.web;

import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToRosterCommand;
import ch.unisg.tapasroster.roster.application.port.in.AssignTaskToRosterUseCase;
import ch.unisg.tapasroster.roster.domain.Task;
import ch.unisg.tapasroster.roster.domain.TaskAssignmentReply;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@RestController
public class AssignTaskToRosterWebController {
    private final AssignTaskToRosterUseCase assignTaskToRosterUseCase;

    public AssignTaskToRosterWebController(AssignTaskToRosterUseCase assignTaskToRosterUseCase) {
        this.assignTaskToRosterUseCase = assignTaskToRosterUseCase;
    }

    @PostMapping(path = "/roster/newtask/", consumes = {TaskAssignmentMediaType.MEDIA_TYPE})
    public ResponseEntity<String> assignTaskToRoster(@RequestBody Task task) {
        try {
            System.out.println(task);

            AssignTaskToRosterCommand command = new AssignTaskToRosterCommand(task);

            Optional<TaskAssignmentReply> reply = assignTaskToRosterUseCase.assignTaskToRoster(command);

            if (reply.isEmpty()) {
                System.out.println("Reply is empty!");
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }

            System.out.println("TaskAssignmentReply: " + reply.get());

            // Add the content type as a response header
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskAssignmentMediaType.MEDIA_TYPE);

            return new ResponseEntity<>(TaskAssignmentMediaType.serialize(reply.get()), responseHeaders, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
