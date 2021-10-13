package ch.unisg.tapasexecutorrobot.executor.application.service;

import ch.unisg.tapasexecutorrobot.executor.application.port.in.ExecuteTaskCommand;
import ch.unisg.tapasexecutorrobot.executor.application.port.in.ExecuteTaskUseCase;
import ch.unisg.tapasexecutorrobot.executor.domain.Task;
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

        var payload = command.getTask().getTaskPayload().getValue();
        System.out.println(payload);

        return task;
    }
}
