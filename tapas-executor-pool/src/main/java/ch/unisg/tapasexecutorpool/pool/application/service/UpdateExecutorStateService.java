package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapasexecutorpool.pool.application.port.in.UpdateExecutorStateCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.UpdateExecutorStateUseCase;
import ch.unisg.tapasexecutorpool.pool.domain.ExecutorPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class UpdateExecutorStateService implements UpdateExecutorStateUseCase {
    @Override
    public boolean updateExecutorState(UpdateExecutorStateCommand command) {
        var executorPool = ExecutorPool.getTapasExecutorPool();

        var id = command.getExecutorId();
        var state = command.getExecutorState();

        System.out.println("Update State (=" + state.getValue() + ") for Executor Id (=" + id.getValue() + ")");

        return executorPool.updateExecutorState(command.getExecutorId(), command.getExecutorState());
    }
}
