package ch.unisg.tapas.auctionhouse.application.handler;

import ch.unisg.tapas.auctionhouse.application.port.in.TaskWonEvent;
import ch.unisg.tapas.auctionhouse.application.port.in.TaskWonEventHandler;
import ch.unisg.tapascommon.ServiceHostAddresses;
import ch.unisg.tapascommon.communication.WebClient;
import ch.unisg.tapascommon.tasks.adapter.in.formats.TaskJsonRepresentation;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class TaskWonHandler implements TaskWonEventHandler {

    private static final Logger LOGGER = LogManager.getLogger(TaskWonHandler.class);

    @Override
    public WonTaskStatus handleAuctionWon(TaskWonEvent event) {
        try {
            WebClient.post(
                ServiceHostAddresses.getTaskServiceHostAddress() + "/tasks",
                event.getTaskJsonRepresentation().serialize(),
                TaskJsonRepresentation.MEDIA_TYPE);
            LOGGER.info("Delegated task forwarded to task list service");
            return WonTaskStatus.OK;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            LOGGER.warn("Failed to forward delegated task");
            return WonTaskStatus.CANNOT_EXECUTE;
        }
    }
}
