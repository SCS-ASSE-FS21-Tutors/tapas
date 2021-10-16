package ch.unisg.tapasexecutorpool.executorpool.application.service;

import ch.unisg.tapasexecutorpool.executorpool.application.port.in.RetrieveExecutorByIdQuery;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.RetrieveExecutorByIdUseCase;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import ch.unisg.tapasexecutorpool.executorpool.domain.ExecutorPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Transactional
public class RetrieveExecutorByIdService implements RetrieveExecutorByIdUseCase {
    @Override
    public Optional<Executor> retrieveExecutorById(RetrieveExecutorByIdQuery query) {
        ExecutorPool executorPool = ExecutorPool.getTapasExecutorPool();
        Optional<Executor> retrievedExecutor =  executorPool.retrieveExecutorById(query.getExecutorId());
        return retrievedExecutor;
    }
}