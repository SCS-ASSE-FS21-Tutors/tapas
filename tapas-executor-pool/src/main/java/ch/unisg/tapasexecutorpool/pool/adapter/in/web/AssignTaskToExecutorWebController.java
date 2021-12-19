package ch.unisg.tapasexecutorpool.pool.adapter.in.web;

import ch.unisg.tapasexecutorpool.common.formats.TaskJsonRepresentation;
import ch.unisg.tapasexecutorpool.pool.adapter.in.web.dto.CanExecuteDto;
import ch.unisg.tapasexecutorpool.pool.application.port.in.AssignTaskCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.AssignTaskUseCase;
import ch.unisg.tapasexecutorpool.pool.application.port.in.CanExecuteTaskQuery;
import ch.unisg.tapasexecutorpool.pool.application.port.in.EnqueueTaskUseCase;
import ch.unisg.tapasexecutorpool.pool.application.service.NoExecutorFoundException;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@AllArgsConstructor
@RestController
@Log4j2
public class AssignTaskToExecutorWebController {
    private final EnqueueTaskUseCase enqueueTaskUseCase;
    private final CanExecuteTaskQuery canExecuteTaskQuery;

    @PostMapping(path="/can-execute/", consumes = {TaskJsonRepresentation.MEDIA_TYPE})
    public CanExecuteDto canExecuteTask(@RequestBody TaskJsonRepresentation taskJsonRepresentation){
        Task task = TaskJsonRepresentation.toTask(taskJsonRepresentation);
        log.info("Received query if task {} of type {} can be executed",
                task.getTaskId().getValue(), task.getTaskType().getValue());
        var canExecute = canExecuteTaskQuery.canExecute(task);
        return new CanExecuteDto(canExecute);
    }

    @PostMapping(path = "/execute", consumes = {TaskJsonRepresentation.MEDIA_TYPE})
    public ResponseEntity assignTaskToExecutor(@RequestParam Boolean external, @RequestBody TaskJsonRepresentation taskJsonRepresentation) {
        Task task = TaskJsonRepresentation.toTask(taskJsonRepresentation);
        log.info("Received request to execute task {} of type {}",
                task.getTaskId().getValue(), task.getTaskType().getValue());
        task.setExternal(external);
        enqueueTaskUseCase.enqueueTask(task);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
