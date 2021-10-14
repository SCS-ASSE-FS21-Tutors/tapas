package ch.unisg.tapasroster.roster.application.service;

import ch.unisg.tapasroster.roster.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.tapasroster.roster.application.port.in.AddNewExecutorToExecutorPoolUseCase;
import ch.unisg.tapasroster.roster.domain.Executor;
import ch.unisg.tapasroster.roster.domain.ExecutorPool;
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

        return executorPool.addNewExecutorWithNameAndType(command.getExecutorName(), command.getTaskType());
    }
}
