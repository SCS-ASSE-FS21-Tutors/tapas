package ch.unisg.roster.roster.adapter.in.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.unisg.roster.roster.application.port.in.ApplyForTaskCommand;
import ch.unisg.roster.roster.application.port.in.ApplyForTaskUseCase;
import ch.unisg.roster.roster.domain.ExecutorInfo;
import ch.unisg.roster.roster.domain.Task;

@RestController
public class ApplyForTaskController {
    private final ApplyForTaskUseCase applyForTaskUseCase;

    public ApplyForTaskController(ApplyForTaskUseCase applyForTaskUseCase) {
        this.applyForTaskUseCase = applyForTaskUseCase;
    }

    /**
    *   Checks if task is available for the requesting executor.
    *   @return a task or null if no task found
    **/
    @PostMapping(path = "/task/apply", consumes = {"application/json"})
    public Task applyForTask(@RequestBody ExecutorInfo executorInfo) {

        ApplyForTaskCommand command = new ApplyForTaskCommand(executorInfo.getExecutorType(),
            executorInfo.getExecutorURI());

        return applyForTaskUseCase.applyForTask(command);
    }
}
