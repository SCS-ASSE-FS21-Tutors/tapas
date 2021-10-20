package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapasexecutorpool.pool.application.port.in.ExecuteTaskCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.ExecuteTaskUseCase;
import ch.unisg.tapasexecutorpool.pool.application.port.out.ForwardTaskToExecutorEventPort;
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
        var executor = pool.retrieveExecutorByTaskType(task.getTaskType());

        if (executor.isPresent()) {
            var event = new ForwardTaskToExecutorEvent(task, executor.get());
            forwardTaskToExecutorEventPort.forwardTaskToPoolEvent(event);
        }

        return task;
    }
}
