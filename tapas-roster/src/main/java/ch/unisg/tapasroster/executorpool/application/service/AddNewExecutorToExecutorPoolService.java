package ch.unisg.tapasroster.executorpool.application.service;

import ch.unisg.tapasroster.executorpool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.tapasroster.executorpool.application.port.in.AddNewExecutorToExecutorPoolUseCase;
import ch.unisg.tapasroster.executorpool.domain.Executor;
import ch.unisg.tapasroster.executorpool.domain.ExecutorPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class AddNewExecutorToExecutorPoolService implements AddNewExecutorToExecutorPoolUseCase {

    @Override
    public Executor addNewExecutorToExecutorPool(AddNewExecutorToExecutorPoolCommand command) {
        ExecutorPool executorPool = ExecutorPool.getTapasExecutorPool();

        return executorPool.addNewExecutorWithNameAndType(
                command.getExecutorName(), command.getExecutorServer(),
                command.getExecutorPort(),  command.getTaskType());
    }
}
