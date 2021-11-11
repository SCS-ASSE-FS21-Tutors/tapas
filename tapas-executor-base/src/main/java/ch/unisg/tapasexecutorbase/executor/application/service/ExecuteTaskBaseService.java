package ch.unisg.tapasexecutorbase.executor.application.service;

import ch.unisg.tapasexecutorbase.executor.application.port.in.ExecuteTaskCommand;
import ch.unisg.tapasexecutorbase.executor.application.port.in.ExecuteTaskUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExecuteTaskBaseService implements ExecuteTaskUseCase {

    @Override
    public void executeTask(ExecuteTaskCommand command) { }
}
