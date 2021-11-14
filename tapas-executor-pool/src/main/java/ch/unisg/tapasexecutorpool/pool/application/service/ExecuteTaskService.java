package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapasexecutorpool.pool.application.port.in.ExecuteTaskCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.ExecuteTaskUseCase;
import ch.unisg.tapasexecutorpool.pool.application.port.out.ForwardTaskToExecutorEventPort;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.ExecutorPool;
import ch.unisg.tapasexecutorpool.pool.domain.ForwardTaskToExecutorEvent;
import ch.unisg.tapascommon.tasks.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class ExecuteTaskService implements ExecuteTaskUseCase {

    private final ForwardTaskToExecutorEventPort forwardTaskToExecutorEventPort;

    @Override
    public Task executeTask(ExecuteTaskCommand command) {
        var task = command.getTask();
        System.out.println(command.getTask().getTaskId().getValue());
        System.out.println(command.getTask().getTaskName().getValue());
        System.out.println(command.getTask().getTaskType().getValue());
        System.out.println(command.getTask().getInputData().getValue());

        var pool = ExecutorPool.getTapasExecutorPool();
        var executorOptional = pool.retrieveAvailableExecutorByTaskType(task.getTaskType());

        if (executorOptional.isPresent()) {
            var executor = executorOptional.get();
            var event = new ForwardTaskToExecutorEvent(task, executor);
            forwardTaskToExecutorEventPort.forwardTaskToExecutorEvent(event);
        }

        return task;
    }
}
