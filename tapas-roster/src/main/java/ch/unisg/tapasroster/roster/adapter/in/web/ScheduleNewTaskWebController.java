package ch.unisg.tapasroster.roster.adapter.in.web;

import ch.unisg.tapasroster.roster.application.port.in.ScheduleTaskCommand;
import ch.unisg.tapasroster.roster.application.port.in.ScheduleTaskUseCase;
import ch.unisg.tapasroster.roster.domain.Task;
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

    @PostMapping(path = "/roster/schedule-task/", consumes = {TaskMediaType.TASK_MEDIA_TYPE})
    public ResponseEntity<String> scheduleNewTask(@RequestBody Task task) {
        try {
            var command = new ScheduleTaskCommand(
                    task.getTaskId(),
                    task.getTaskName(),
                    task.getTaskType()
            );

            Task newTask = scheduleTaskUseCase.scheduleTask(command);

            // Add the content type as a response header
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, TaskMediaType.TASK_MEDIA_TYPE);

            return new ResponseEntity<>(TaskMediaType.serialize(task), responseHeaders, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
