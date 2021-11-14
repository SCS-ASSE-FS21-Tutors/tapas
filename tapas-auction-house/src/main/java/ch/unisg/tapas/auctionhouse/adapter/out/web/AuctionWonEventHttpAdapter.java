package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.application.port.out.AuctionWonEventPort;
import ch.unisg.tapas.auctionhouse.domain.AuctionRegistry;
import ch.unisg.tapas.auctionhouse.domain.AuctionWonEvent;
import ch.unisg.tapascommon.communication.WebClient;
import ch.unisg.tapascommon.tasks.adapter.in.formats.TaskJsonRepresentation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpResponse;

/**
 * This class is a template for sending auction won events via HTTP. This class was created here only
 * as a placeholder, it is up to you to decide how such events should be sent (e.g., via HTTP,
 * WebSub, etc.).
 */
@Component
@Primary
public class AuctionWonEventHttpAdapter implements AuctionWonEventPort {

    private static final Logger LOGGER = LogManager.getLogger(AuctionWonEventHttpAdapter.class);

    private static final String PATH = "/taskwinner";

    @Override
    public void publishAuctionWonEvent(AuctionWonEvent event) {
        var winningBid = event.getWinningBid();

        var auctionId = winningBid.getAuctionId();
        var auctionOptional = AuctionRegistry.getInstance().getAuctionById(auctionId);

        if (auctionOptional.isPresent()) {
            var auction = auctionOptional.get();
            var taskUri = auction.getTaskUri();
            HttpResponse<String> taskResponse = null;
            try {
                taskResponse = WebClient.get(taskUri.toString());
            } catch (IOException | InterruptedException e) {
                LOGGER.warn("Failed to retrieve the task from task list");
                return;
            }

            var taskJson = taskResponse.body();
            var contentTypeOptional = taskResponse.headers().firstValue("Content-Type");
            var taskJsonContentType = contentTypeOptional.orElse(TaskJsonRepresentation.MEDIA_TYPE);

            var auctionHouseUri = winningBid.getBidderAuctionHouseUri().getValue().toString();

            if (!auctionHouseUri.contains(PATH)) {
                auctionHouseUri += PATH;
            }
            auctionHouseUri = auctionHouseUri.replace("//", "/");

            try {
                var response = WebClient.post(auctionHouseUri, taskJson, taskJsonContentType);
                if (WebClient.checkResponseStatusCode(response)) {
                    LOGGER.info("Successfully notified the winning auction house");
                } else {
                    LOGGER.warn("Failed to notify the winning auction house");
                }
            } catch (IOException | InterruptedException ignored) {
                LOGGER.warn("Failed to notify the winning auction house");
            }
        } else {
            LOGGER.warn("Failed to retrieve the auction from the auction registry");
        }
    }
}
