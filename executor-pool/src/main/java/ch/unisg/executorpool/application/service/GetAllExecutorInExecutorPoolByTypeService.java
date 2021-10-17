package ch.unisg.executorpool.application.service;

import ch.unisg.executorpool.application.port.in.GetAllExecutorInExecutorPoolByTypeQuery;
import ch.unisg.executorpool.application.port.in.GetAllExecutorInExecutorPoolByTypeUseCase;
import ch.unisg.executorpool.domain.ExecutorClass;
import ch.unisg.executorpool.domain.ExecutorPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Component
@Transactional
public class GetAllExecutorInExecutorPoolByTypeService implements GetAllExecutorInExecutorPoolByTypeUseCase {

    @Override
    public List<ExecutorClass> getAllExecutorInExecutorPoolByType(GetAllExecutorInExecutorPoolByTypeQuery query){
        ExecutorPool executorPool = ExecutorPool.getExecutorPool();
        return executorPool.getAllExecutorsByType(query.getExecutorTaskType());
    }

}
