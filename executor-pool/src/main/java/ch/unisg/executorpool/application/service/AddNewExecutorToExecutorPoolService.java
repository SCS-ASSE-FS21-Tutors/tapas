package ch.unisg.executorpool.application.service;

import ch.unisg.executorpool.application.port.in.AddNewExecutorToExecutorPoolUseCase;
import ch.unisg.executorpool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.executorpool.application.port.out.ExecutorAddedEventPort;
import ch.unisg.executorpool.domain.ExecutorAddedEvent;
import ch.unisg.executorpool.domain.ExecutorClass;
import ch.unisg.executorpool.domain.ExecutorPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class AddNewExecutorToExecutorPoolService implements AddNewExecutorToExecutorPoolUseCase {

    private final ExecutorAddedEventPort executorAddedEventPort;

    public AddNewExecutorToExecutorPoolService(ExecutorAddedEventPort executorAddedEventPort){
        this.executorAddedEventPort = executorAddedEventPort;
    }

    @Override
    public ExecutorClass addNewExecutorToExecutorPool(AddNewExecutorToExecutorPoolCommand command){
        ExecutorPool executorPool = ExecutorPool.getExecutorPool();
        var newExecutor = executorPool.addNewExecutor(command.getExecutorUri(), command.getExecutorTaskType());

        var executorAddedEvent = new ExecutorAddedEvent(newExecutor);
        executorAddedEventPort.publishExecutorAddedEvent(executorAddedEvent);

        return newExecutor;
    }
}
