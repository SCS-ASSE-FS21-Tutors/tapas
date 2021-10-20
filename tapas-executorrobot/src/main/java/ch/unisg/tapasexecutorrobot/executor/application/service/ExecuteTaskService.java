package ch.unisg.tapasexecutorrobot.executor.application.service;

import ch.unisg.tapasexecutorrobot.executor.application.port.in.ExecuteTaskCommand;
import ch.unisg.tapasexecutorrobot.executor.application.port.in.ExecuteTaskUseCase;
import ch.unisg.tapasexecutorrobot.executor.domain.CherryBot;
import ch.unisg.tapascommon.tasks.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class ExecuteTaskService implements ExecuteTaskUseCase {
    @Override
    public Task executeTask(ExecuteTaskCommand command) {
        var task = command.getTask();

        var payload = command.getTask().getInputData().getValue();
        System.out.println(payload);

        var robot = new CherryBot();
        robot.postOperator();
        robot.putTcp();
        robot.postInitialize();
        robot.deleteOperator();

        task.setOutputData(new Task.OutputData("Operation completed"));

        return task;
    }
}
