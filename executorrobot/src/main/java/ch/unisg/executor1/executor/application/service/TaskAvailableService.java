package ch.unisg.executorrobot.executor.application.service;

import org.springframework.stereotype.Component;

import ch.unisg.executorrobot.executor.domain.Executor;
import ch.unisg.executorbase.executor.application.port.in.TaskAvailableCommand;
import ch.unisg.executorbase.executor.application.port.in.TaskAvailableUseCase;
import ch.unisg.executorbase.executor.domain.ExecutorStatus;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class TaskAvailableService implements TaskAvailableUseCase {

    @Override
    public void newTaskAvailable(TaskAvailableCommand command) {

        Executor executor = Executor.getExecutor();

        if (executor.getExecutorType() == command.getTaskType() &&
            executor.getStatus() == ExecutorStatus.IDLING) {
            executor.getAssignment();
        }
    }
}
