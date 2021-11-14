package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapascommon.tasks.domain.Task;
import ch.unisg.tapasexecutorpool.pool.adapter.in.formats.ExecutorJsonRepresentation;
import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToPoolCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToPoolUseCase;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.ExecutorPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class AddNewExecutorToPoolService implements AddNewExecutorToPoolUseCase {
    public ExecutorJsonRepresentation addNewExecutorToPool(AddNewExecutorToPoolCommand command) {
        var executorPool = ExecutorPool.getTapasExecutorPool();
        var executorRepresentation = command.getExecutorJsonRepresentation();
        var newExecutor = executorPool.addNewExecutor(
                new Executor.ExecutorName(executorRepresentation.getExecutorName()),
                new Executor.ExecutorType(Task.Type.valueOf(executorRepresentation.getExecutorType())),
                new Executor.ExecutorAddress(executorRepresentation.getExecutorAddress())
        );
        var newExecutorRepresentation = new ExecutorJsonRepresentation(newExecutor);
        return newExecutorRepresentation;
    }
}
