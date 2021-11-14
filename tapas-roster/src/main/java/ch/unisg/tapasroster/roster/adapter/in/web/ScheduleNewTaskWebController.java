package ch.unisg.tapasroster.roster.adapter.in.web;

import ch.unisg.tapascommon.tasks.adapter.in.formats.TaskJsonRepresentation;
import ch.unisg.tapascommon.tasks.domain.Task;
import ch.unisg.tapasroster.roster.application.port.in.ScheduleTaskCommand;
import ch.unisg.tapasroster.roster.application.port.in.ScheduleTaskUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RestController
public class ScheduleNewTaskWebController {
    private final ScheduleTaskUseCase scheduleTaskUseCase;

    public ScheduleNewTaskWebController(ScheduleTaskUseCase scheduleTaskUseCase) {
        this.scheduleTaskUseCase = scheduleTaskUseCase;
    }

    @PostMapping(path = "/roster/schedule-task/", consumes = {TaskJsonRepresentation.MEDIA_TYPE})
    public ResponseEntity<Void> scheduleNewTask(@RequestBody TaskJsonRepresentation task) {
        try {
            var command = new ScheduleTaskCommand(task.deserialize());
            var isScheduled = scheduleTaskUseCase.scheduleTask(command);
            return isScheduled ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
