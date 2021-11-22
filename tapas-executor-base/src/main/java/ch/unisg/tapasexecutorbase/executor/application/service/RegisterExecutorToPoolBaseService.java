package ch.unisg.tapasexecutorbase.executor.application.service;

import ch.unisg.tapascommon.communication.ServiceHostAddresses;
import ch.unisg.tapascommon.communication.WebClient;
import ch.unisg.tapascommon.pool.adapter.in.formats.ExecutorJsonRepresentation;
import ch.unisg.tapasexecutorbase.executor.application.port.in.RegisterExecutorToPoolCommand;
import ch.unisg.tapasexecutorbase.executor.application.port.in.RegisterExecutorToPoolUseCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class RegisterExecutorToPoolBaseService implements RegisterExecutorToPoolUseCase {

    private static final Logger LOGGER = LogManager.getLogger(RegisterExecutorToPoolBaseService.class);

    public static boolean registerThisExecutorToPool(String id, String name, String type, String address) {
        try {
            WebClient.post(
                    ServiceHostAddresses.getExecutorPoolServiceHostAddress() + "/executors/",
                    new ExecutorJsonRepresentation(id, name, type, address).serialize(),
                    ExecutorJsonRepresentation.MEDIA_TYPE
            );
            LOGGER.info("Registered this Executor to Pool");
            return true;
        } catch (IOException | InterruptedException e) {
            LOGGER.warn("Failed to register this Executor to Pool");
            return false;
        }
    }

    @Override
    public boolean registerToPool(RegisterExecutorToPoolCommand command) {
        return false;
    }
}
