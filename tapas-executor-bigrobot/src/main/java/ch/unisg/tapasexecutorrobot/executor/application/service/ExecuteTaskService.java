package ch.unisg.tapasexecutorrobot.executor.application.service;

import ch.unisg.tapasexecutorbase.executor.application.port.in.ExecuteTaskCommand;
import ch.unisg.tapasexecutorbase.executor.application.port.in.ExecuteTaskUseCase;
import ch.unisg.tapasexecutorbase.executor.application.port.out.ExecutorStateChangedEventPort;
import ch.unisg.tapasexecutorbase.executor.application.port.out.TaskUpdatedEventPort;
import ch.unisg.tapasexecutorbase.executor.application.service.ExecuteTaskBaseService;
import ch.unisg.tapasexecutorbase.executor.config.ExecutorConfig;
import ch.unisg.tapasexecutorrobot.executor.domain.Cherrybot;
import ch.unisg.tapascommon.tasks.domain.Task;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Primary
@Component
public class ExecuteTaskService implements ExecuteTaskUseCase {

    private static final Logger LOGGER = LogManager.getLogger(ExecuteTaskService.class);

    private final ExecutorConfig executorConfig;
    private final ExecutorStateChangedEventPort executorStateChangedEventPort;
    private final TaskUpdatedEventPort taskUpdatedEventPort;

    private void moveRobot() {
        LOGGER.info("Start moving Robot");
        var robot = new Cherrybot();
        robot.postOperator();
        robot.putTcp();
        robot.postInitialize();
        robot.deleteOperator();
        LOGGER.info("Finished moving Robot");
    }

    @Override
    public void executeTask(ExecuteTaskCommand command) {
        var executorBaseService = new ExecuteTaskBaseService(
                executorConfig, executorStateChangedEventPort, taskUpdatedEventPort
        );

        executorBaseService.updateExecutorState("BUSY");

        var task = command.getTask();

        task.setTaskStatus(new Task.TaskStatus(Task.Status.RUNNING));
        executorBaseService.updateTaskStatus(task);

        moveRobot();

        task.setOutputData(new Task.OutputData("COMPLETED"));
        task.setTaskStatus(new Task.TaskStatus(Task.Status.EXECUTED));
        executorBaseService.updateTaskStatus(task);

        executorBaseService.updateExecutorState("IDLE");
    }
}
