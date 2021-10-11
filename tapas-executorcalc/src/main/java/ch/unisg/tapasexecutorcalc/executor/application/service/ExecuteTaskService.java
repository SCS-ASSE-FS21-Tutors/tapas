package ch.unisg.tapasexecutorcalc.executor.application.service;

import ch.unisg.tapasexecutorcalc.executor.application.port.in.ExecuteTaskCommand;
import ch.unisg.tapasexecutorcalc.executor.application.port.in.ExecuteTaskUseCase;
import ch.unisg.tapasexecutorcalc.executor.domain.Task;
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

        System.out.println(command.getTask().getTaskPayload().getValue());

        return task;
    }
}
