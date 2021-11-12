package ch.unisg.tapas.auctionhouse.adapter.common.clients;

import ch.unisg.tapascommon.communication.WebClient;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WebSubPublisher {

    private static final Logger LOGGER = LogManager.getLogger(WebSubPublisher.class);

    private static final String CONTENT_TYPE_PRODUCTION = "application/x-www-form-urlencoded";
    private static final String CONTENT_TYPE_DEVELOPMENT = "application/json";

    private final WebSubConfig webSubConfig;

    public boolean notifyHub(String path) {

        // TODO: Implement production

        var json = new JSONObject()
            .put("hub.mode", "publish")
            .put("hub.url", webSubConfig.getSelf() + path)
            .toString();

        var response = WebClient.post(webSubConfig.getHub() + "/publish", json, CONTENT_TYPE_DEVELOPMENT);
        var ok = WebClient.checkResponseStatusCode(response);

        if (ok) {
            LOGGER.info("Successfully notified WebSubHub");
        } else {
            LOGGER.warn("Failed to notify WebSubHub");
        }

        return ok;
    }
}
