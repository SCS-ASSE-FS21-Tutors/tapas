package ch.unisg.tapasexecutorpool.executorpool.application.service;

import ch.unisg.tapasexecutorpool.executorpool.application.port.in.RetrieveAllExecutorsUseCase;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import ch.unisg.tapasexecutorpool.executorpool.domain.ExecutorPool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RetrieveAllExecutorsService implements RetrieveAllExecutorsUseCase {

    @Override
    public List<Executor> getAllExecutors() {
        ExecutorPool executorPool = ExecutorPool.getTapasExecutorPool();
        return executorPool.getListOfExecutors().getValue();
    }
}
