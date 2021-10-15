package ch.unisg.tapasexecutorpool.executorpool.application.service;

import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddExecutorToPoolCommand;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddExecutorToPoolUseCase;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;

import ch.unisg.tapasexecutorpool.executorpool.domain.ExecutorPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class AddExecutorToPoolService implements AddExecutorToPoolUseCase {

    @Override
    public Executor addNewExecutorToExecutorPool(AddExecutorToPoolCommand command) {
        ExecutorPool executorPool = ExecutorPool.getTapasExecutorPool();
        Executor newExecutor = executorPool.addNewExecutorWithUrlAndType(command.getExecutorUrl(), command.getExecutorType());
        return newExecutor;
    }
}
