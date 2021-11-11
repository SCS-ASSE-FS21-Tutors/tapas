package ch.unisg.tapasexecutorpool.pool.application.service;

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

    public Executor addNewExecutorToPool(AddNewExecutorToPoolCommand command) {
        var executorPool = ExecutorPool.getTapasExecutorPool();
        var newExecutor = executorPool.addNewExecutor(command.getExecutorName(), command.getExecutorType(), command.getExecutorAddress());
        return newExecutor;
    }
}
