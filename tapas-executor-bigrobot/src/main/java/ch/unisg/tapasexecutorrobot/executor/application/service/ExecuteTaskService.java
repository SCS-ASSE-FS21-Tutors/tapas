package ch.unisg.tapasexecutorrobot.executor.application.service;

import ch.unisg.tapascommon.ServiceHostAddresses;
import ch.unisg.tapascommon.tasks.adapter.in.formats.TaskJsonRepresentation;
import ch.unisg.tapasexecutorbase.executor.application.port.in.ExecuteTaskCommand;
import ch.unisg.tapasexecutorbase.executor.application.port.in.ExecuteTaskUseCase;
import ch.unisg.tapasexecutorbase.executor.application.port.out.ExecutorStateChangedEventPort;
import ch.unisg.tapasexecutorbase.executor.application.port.out.TaskUpdatedEventPort;
import ch.unisg.tapasexecutorbase.executor.application.service.ExecuteTaskBaseService;
import ch.unisg.tapasexecutorbase.executor.domain.ExecutorStateChangedEvent;
import ch.unisg.tapasexecutorbase.executor.domain.TaskUpdatedEvent;
import ch.unisg.tapasexecutorrobot.executor.domain.Cherrybot;
import ch.unisg.tapascommon.tasks.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Primary
@Component
public class ExecuteTaskService implements ExecuteTaskUseCase {

    private final ExecutorStateChangedEventPort executorStateChangedEventPort;
    private final TaskUpdatedEventPort taskUpdatedEventPort;

    private void updateExecutorState(String state) {
        executorStateChangedEventPort.publishExecutorStateChangedEvent(
                new ExecutorStateChangedEvent(Task.Type.BIGROBOT.name(), state)
        );
    }

    private void moveRobot() {
        var robot = new Cherrybot();
        robot.postOperator();
        robot.putTcp();
        robot.postInitialize();
        robot.deleteOperator();
    }

    @Override
    public void executeTask(ExecuteTaskCommand command) {
        updateExecutorState("BUSY");

        var task = command.getTask();

        task.setTaskStatus(new Task.TaskStatus(Task.Status.RUNNING));
        ExecuteTaskBaseService.updateTaskStatus(task, taskUpdatedEventPort);

        moveRobot();
        task.setOutputData(new Task.OutputData("COMPLETED"));

        task.setTaskStatus(new Task.TaskStatus(Task.Status.EXECUTED));
        ExecuteTaskBaseService.updateTaskStatus(task, taskUpdatedEventPort);

        updateExecutorState("IDLE");
    }
}
