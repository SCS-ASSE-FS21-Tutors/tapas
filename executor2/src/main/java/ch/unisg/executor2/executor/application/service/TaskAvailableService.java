package ch.unisg.executor2.executor.application.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import ch.unisg.executor2.executor.domain.Executor;
import ch.unisg.executorBase.executor.application.port.in.TaskAvailableCommand;
import ch.unisg.executorBase.executor.application.port.in.TaskAvailableUseCase;
import ch.unisg.executorBase.executor.domain.ExecutorStatus;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class TaskAvailableService implements TaskAvailableUseCase {

    @Override
    @Async
    public void newTaskAvailable(TaskAvailableCommand command) {

        Executor executor = Executor.getExecutor();

        if (executor.getExecutorType() == command.getTaskType() &&
            executor.getStatus() == ExecutorStatus.IDLING) {
            executor.getAssignment();
        }
    }
}
