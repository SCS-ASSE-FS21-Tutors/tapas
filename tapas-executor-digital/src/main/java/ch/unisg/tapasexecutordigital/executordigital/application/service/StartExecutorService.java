package ch.unisg.tapasexecutordigital.executordigital.application.service;

import ch.unisg.tapasexecutordigital.executordigital.application.port.in.StartExecutorUseCase;
import ch.unisg.tapasexecutordigital.executordigital.application.port.out.ExecutorStateChangedPort;
import ch.unisg.tapasexecutordigital.executordigital.domain.Executor;
import ch.unisg.tapasexecutordigital.executordigital.domain.Executor.ExecutorState;
import ch.unisg.tapasexecutordigital.executordigital.domain.ExecutorStateChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class StartExecutorService implements StartExecutorUseCase {

    private final ExecutorStateChangedPort executorStateChangedPort;

    @Override
    public void startExecutor() {
        Executor executor = Executor.getExecutor();
        if(executor.getAssignedTask() != null) {
            executor.startTask();
        }
        if(executor.getAssignedTask() != null) {
            executor.setExecutorState(new ExecutorState(Executor.State.RUNNING));
            ExecutorStateChangedEvent stateChange = new ExecutorStateChangedEvent(executor.getExecutorName().getValue(),
                    executor.getExecutorState().getValue().toString());
            executorStateChangedPort.publishExecutorStateChanged(stateChange);
        }
    }
}
