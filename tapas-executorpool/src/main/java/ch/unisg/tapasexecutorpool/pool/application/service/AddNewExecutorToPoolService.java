package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToPoolCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToPoolUseCase;
import ch.unisg.tapasexecutorpool.pool.application.port.out.NewExecutorAddedEventPort;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.ExecutorPool;
import ch.unisg.tapasexecutorpool.pool.domain.NewExecutorAddedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class AddNewExecutorToPoolService implements AddNewExecutorToPoolUseCase {

    private final NewExecutorAddedEventPort newExecutorAddedEventPort;

    public Executor addNewExecutorToPool(AddNewExecutorToPoolCommand command) {
        ExecutorPool executorPool = ExecutorPool.getTapasExecutorPool();
        Executor newExecutor = executorPool.addNewExecutor(command.getExecutorName(), command.getExecutorType(), command.getExecutorAddress());

        if (newExecutor != null) {
            NewExecutorAddedEvent newExecutorAdded = new NewExecutorAddedEvent(newExecutor.getExecutorName().getValue(),
                    executorPool.getExecutorPoolName().getValue());
            newExecutorAddedEventPort.publishNewExecutorAddedEvent(newExecutorAdded);
        }

        return newExecutor;
    }
}
