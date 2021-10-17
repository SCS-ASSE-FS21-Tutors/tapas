package ch.unisg.executorpool.application.service;

import ch.unisg.executorpool.application.port.in.AddNewExecutorToExecutorPoolUseCase;
import ch.unisg.executorpool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.executorpool.domain.ExecutorClass;
import ch.unisg.executorpool.domain.ExecutorPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

@RequiredArgsConstructor
@Component
@Transactional
public class AddNewExecutorToExecutorPoolService implements AddNewExecutorToExecutorPoolUseCase {

    @Override
    public ExecutorClass addNewExecutorToExecutorPool(AddNewExecutorToExecutorPoolCommand command){
        ExecutorPool executorPool = ExecutorPool.getExecutorPool();

        return executorPool.addNewExecutor(command.getExecutorIp(), command.getExecutorPort(), command.getExecutorTaskType());
    }
}
