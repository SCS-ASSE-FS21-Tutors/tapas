package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapascommon.ServiceHostAddresses;
import ch.unisg.tapasexecutorpool.pool.application.port.in.ExecuteTaskCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.ExecuteTaskUseCase;
import ch.unisg.tapasexecutorpool.pool.application.port.out.ForwardTaskToExecutorEventPort;
import ch.unisg.tapasexecutorpool.pool.application.port.out.TaskAssignedEvent;
import ch.unisg.tapasexecutorpool.pool.application.port.out.TaskAssignedEventPort;
import ch.unisg.tapasexecutorpool.pool.domain.ExecutorPool;
import ch.unisg.tapasexecutorpool.pool.domain.ForwardTaskToExecutorEvent;
import ch.unisg.tapascommon.tasks.domain.Task;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class ExecuteTaskService implements ExecuteTaskUseCase {

    private static final Logger LOGGER = LogManager.getLogger(ExecuteTaskService.class);

    private final TaskAssignedEventPort taskAssignedEventPort;
    private final ForwardTaskToExecutorEventPort forwardTaskToExecutorEventPort;

    @Override
    public Task executeTask(ExecuteTaskCommand command) {
        var task = command.getTask();
        LOGGER.info("Executing new Task: " + task);

        var pool = ExecutorPool.getTapasExecutorPool();
        var executorOptional = pool.retrieveAvailableExecutorByTaskType(task.getTaskType());

        if (executorOptional.isPresent()) {
            var taskId = task.getTaskId().getValue();

            taskAssignedEventPort.handleTaskAssignedEvent(
                    new TaskAssignedEvent(
                            "tapas-group4",
                            ServiceHostAddresses.getTaskServiceHostAddress() + "/tasks/" + taskId
                    )
            );

            forwardTaskToExecutorEventPort.forwardTaskToExecutorEvent(
                    new ForwardTaskToExecutorEvent(
                            task, executorOptional.get()
                    )
            );
        }

        return task;
    }
}
