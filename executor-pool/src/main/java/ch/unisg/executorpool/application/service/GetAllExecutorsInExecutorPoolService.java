package ch.unisg.executorpool.application.service;

import ch.unisg.executorpool.application.port.in.GetAllExecutorsInExecutorPoolUseCase;
import ch.unisg.executorpool.domain.ExecutorClass;
import ch.unisg.executorpool.domain.ExecutorPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Component
@Transactional
public class GetAllExecutorsInExecutorPoolService implements GetAllExecutorsInExecutorPoolUseCase {

    @Override
    public List<ExecutorClass> getAllExecutorsInExecutorPool(){
        ExecutorPool executorPool = ExecutorPool.getExecutorPool();
        return executorPool.getListOfExecutors().getValue();
    }
}
