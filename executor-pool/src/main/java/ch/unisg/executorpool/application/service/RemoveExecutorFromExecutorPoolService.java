package ch.unisg.executorpool.application.service;

import ch.unisg.executorpool.application.port.in.RemoveExecutorFromExecutorPoolCommand;
import ch.unisg.executorpool.application.port.in.RemoveExecutorFromExecutorPoolUseCase;
import ch.unisg.executorpool.domain.ExecutorClass;
import ch.unisg.executorpool.domain.ExecutorPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Transactional
public class RemoveExecutorFromExecutorPoolService implements RemoveExecutorFromExecutorPoolUseCase {
    @Override
    public Optional<ExecutorClass> removeExecutorFromExecutorPool(RemoveExecutorFromExecutorPoolCommand command){
        ExecutorPool executorPool = ExecutorPool.getExecutorPool();
        return executorPool.removeExecutorByIpAndPort(command.getExecutorUri());
    }
}
