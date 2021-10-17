package ch.unisg.tapasexecutorpool.pool.adapter.in.web;

import ch.unisg.tapasexecutorpool.pool.application.port.in.AssignTaskCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.AssignTaskUseCase;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RestController
public class AssignTaskToExecutorWebController {
    private final AssignTaskUseCase assignTaskUseCase;

    public AssignTaskToExecutorWebController(AssignTaskUseCase assignTaskUseCase) {
        this.assignTaskUseCase = assignTaskUseCase;
    }

    @PostMapping(path = "/assignment/", consumes = {TaskMediaType.TASK_MEDIA_TYPE})
    public ResponseEntity<String> assignTaskToExecutor(@RequestBody Task task) {
        try {
            AssignTaskCommand command = new AssignTaskCommand(
                    task.getTaskId(), task.getTaskName(), task.getTaskType()
            );

            Executor assignedExecutor = assignTaskUseCase.assignTask(command);

            // Add the content type as a response header
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, ExecutorMediaType.EXECUTOR_MEDIA_TYPE);

            return new ResponseEntity<>(ExecutorMediaType.serialize(assignedExecutor), responseHeaders, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
