package ch.unisg.executorpool.application.service;

import ch.unisg.executorpool.application.port.in.RemoveExecutorFromExecutorPoolCommand;
import ch.unisg.executorpool.application.port.in.RemoveExecutorFromExecutorPoolUseCase;
import ch.unisg.executorpool.application.port.out.ExecutorRemovedEventPort;
import ch.unisg.executorpool.domain.ExecutorClass;
import ch.unisg.executorpool.domain.ExecutorPool;
import ch.unisg.executorpool.domain.ExecutorRemovedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@Transactional
public class RemoveExecutorFromExecutorPoolService implements RemoveExecutorFromExecutorPoolUseCase {

    private final ExecutorRemovedEventPort executorRemovedEventPort;

    public RemoveExecutorFromExecutorPoolService(ExecutorRemovedEventPort executorRemovedEventPort){
        this.executorRemovedEventPort = executorRemovedEventPort;
    }

    @Override
    public Optional<ExecutorClass> removeExecutorFromExecutorPool(RemoveExecutorFromExecutorPoolCommand command){
        ExecutorPool executorPool = ExecutorPool.getExecutorPool();
        var removedExecutor = executorPool.removeExecutorByIpAndPort(command.getExecutorUri());

        if(removedExecutor.isPresent()){
            var executorRemovedEvent = new ExecutorRemovedEvent(removedExecutor.get());
            executorRemovedEventPort.publishExecutorRemovedEvent(executorRemovedEvent);
        }

        return removedExecutor;
    }
}
