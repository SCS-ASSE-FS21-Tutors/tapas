package ch.unisg.tapasexecutordigital.executordigital.application.port.in;

import ch.unisg.tapasexecutordigital.common.SelfValidating;

public class StartExecutorCommand extends SelfValidating<StartExecutorUseCase> {

    public StartExecutorCommand() {
        // no information to be transmitted
    }
}