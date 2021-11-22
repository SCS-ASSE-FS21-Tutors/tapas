package ch.unisg.tapasexecutorrobot.executor.application.service;

import ch.unisg.tapascommon.communication.ServiceHostAddresses;
import ch.unisg.tapascommon.tasks.domain.Task;
import ch.unisg.tapasexecutorbase.executor.application.port.in.RegisterExecutorToPoolCommand;
import ch.unisg.tapasexecutorbase.executor.application.port.in.RegisterExecutorToPoolUseCase;
import ch.unisg.tapasexecutorbase.executor.application.service.RegisterExecutorToPoolBaseService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class RegisterExecutorToPoolService implements RegisterExecutorToPoolUseCase {
    @Override
    public boolean registerToPool(RegisterExecutorToPoolCommand command) {
        return RegisterExecutorToPoolBaseService.registerThisExecutorToPool(
                "BigRobotExecutor",
                "BigRobotExecutor",
                Task.Type.BIGROBOT.name(),
                ServiceHostAddresses.getExecutorBigrobotServiceHostAddress()
        );
    }
}
