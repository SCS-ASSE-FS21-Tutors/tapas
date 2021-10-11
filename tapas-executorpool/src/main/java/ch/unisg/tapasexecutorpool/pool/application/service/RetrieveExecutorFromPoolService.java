package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapasexecutorpool.pool.application.port.in.RetrieveExecutorFromPoolCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.RetrieveExecutorFromPoolUseCase;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.ExecutorPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Transactional
public class RetrieveExecutorFromPoolService implements RetrieveExecutorFromPoolUseCase {
    @Override
    public Optional<Executor> retrieveExecutorFromPool(RetrieveExecutorFromPoolCommand command) {
        ExecutorPool executorPool = ExecutorPool.getTapasExecutorPool();
        return executorPool.retrieveExecutorById(command.getExecutorId());
    }
}
