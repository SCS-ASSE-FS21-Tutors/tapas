package ch.unisg.executorpool.application.service;

import ch.unisg.executorpool.application.port.in.GetAllExecutorsInExecutorPoolByTypeQuery;
import ch.unisg.executorpool.application.port.in.GetAllExecutorsInExecutorPoolByTypeUseCase;
import ch.unisg.executorpool.domain.ExecutorClass;
import ch.unisg.executorpool.domain.ExecutorPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Component
@Transactional
public class GetAllExecutorsInExecutorPoolByTypeService implements GetAllExecutorsInExecutorPoolByTypeUseCase {

    @Override
    public List<ExecutorClass> getAllExecutorsInExecutorPoolByType(GetAllExecutorsInExecutorPoolByTypeQuery query){
        ExecutorPool executorPool = ExecutorPool.getExecutorPool();
        return executorPool.getAllExecutorsByType(query.getExecutorTaskType());
    }

}
