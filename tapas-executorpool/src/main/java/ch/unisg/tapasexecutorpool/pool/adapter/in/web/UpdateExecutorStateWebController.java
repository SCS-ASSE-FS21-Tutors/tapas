package ch.unisg.tapasexecutorpool.pool.adapter.in.web;

import ch.unisg.tapasexecutorpool.pool.application.port.in.UpdateExecutorStateCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.UpdateExecutorStateUseCase;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.ExecutorPool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateExecutorStateWebController {

    private final UpdateExecutorStateUseCase updateExecutorStateUseCase;

    UpdateExecutorStateWebController(UpdateExecutorStateUseCase updateExecutorStateUseCase) {
        this.updateExecutorStateUseCase = updateExecutorStateUseCase;
    }

    @PutMapping(path = "/executors/{executorId}/{executorState}")
    ResponseEntity<String> updateTaskState(
            @PathVariable String executorId,
            @PathVariable String executorState
    ) {
        // Temporary until Executors are dynamically added to the pool
        var executorIdType = ExecutorPool.getTapasExecutorPool().getExecutorIdForExecutorType(executorId);
        System.out.println(executorIdType);
        if (executorIdType == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var command = new UpdateExecutorStateCommand(
                executorIdType,
                new Executor.ExecutorState(Executor.State.valueOf(executorState))
        );

        var ok = updateExecutorStateUseCase.updateExecutorState(command);
        return ok ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
