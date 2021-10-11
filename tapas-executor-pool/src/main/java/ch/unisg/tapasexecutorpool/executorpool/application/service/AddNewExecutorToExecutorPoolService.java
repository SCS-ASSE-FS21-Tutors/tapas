package ch.unisg.tapasexecutorpool.executorpool.application.service;

import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddNewExecutorToExecutorPoolUseCase;
import ch.unisg.tapasexecutorpool.executorpool.application.port.out.NewExecutorAddedEventPort;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import ch.unisg.tapasexecutorpool.executorpool.domain.ExecutorPool;
import ch.unisg.tapasexecutorpool.executorpool.domain.NewExecutorAddedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class AddNewExecutorToExecutorPoolService implements AddNewExecutorToExecutorPoolUseCase {

    private final NewExecutorAddedEventPort newExecutorAddedEventPort;

    @Override
    public Executor addNewExecutorToExecutorPool(AddNewExecutorToExecutorPoolCommand command) {
        ExecutorPool executorPool = ExecutorPool.getTapasExecutorPool();
        Executor executor = executorPool.addNewExecutorWithNameAndType(command.getExecutorName(), command.getTaskType());

        if (executor != null) {
            NewExecutorAddedEvent newExecutorAdded = new NewExecutorAddedEvent(executor.getExecutorName().getValue(),
                    executorPool.getExecutorPoolName().getValue());
            //newExecutorAddedEventPort.publishNewExecutorAddedEvent(newExecutorAdded);
        }

        return executor;
    }
}
