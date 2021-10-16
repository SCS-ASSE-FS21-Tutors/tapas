package ch.unisg.assignment.assignment.adapter.in.web;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ch.unisg.assignment.assignment.application.port.in.ApplyForTaskCommand;
import ch.unisg.assignment.assignment.application.port.in.ApplyForTaskUseCase;
import ch.unisg.assignment.assignment.domain.ExecutorInfo;
import ch.unisg.assignment.assignment.domain.Task;

@RestController
public class ApplyForTaskController {
    private final ApplyForTaskUseCase applyForTaskUseCase;

    public ApplyForTaskController(ApplyForTaskUseCase applyForTaskUseCase) {
        this.applyForTaskUseCase = applyForTaskUseCase;
    }

    @PostMapping(path = "/task/apply", consumes = {"application/json"})
    public Task applyForTask(@RequestBody ExecutorInfo executorInfo) {
        try {
            ApplyForTaskCommand command = new ApplyForTaskCommand(executorInfo.getExecutorType(),
                executorInfo.getIp(), executorInfo.getPort());

            return applyForTaskUseCase.applyForTask(command);

        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
