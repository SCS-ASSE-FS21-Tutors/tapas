package ch.unisg.tapas.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * Used to access properties provided via application.properties
 */
@Component
public class ConfigProperties {
    @Autowired
    private Environment environment;

    /**
     * Retrieves the URI of the WebSub hub. In this project, we use a single WebSub hub, but we could
     * use multiple.
     *
     * @return the URI of the WebSub hub
     */
    public URI getWebSubHub() {
        return URI.create(environment.getProperty("websub.hub"));
    }

    /**
     * Retrieves the URI used to publish content via WebSub. In this project, we use a single
     * WebSub hub, but we could use multiple. This URI is usually different from the WebSub hub URI.
     *
     * @return URI used to publish content via the WebSub hub
     */
    public URI getWebSubPublishEndpoint() {
        return URI.create(environment.getProperty("websub.hub.publish"));
    }

    /**
     * Retrieves the name of the group providing this auction house.
     *
     * @return the identifier of the group, e.g. tapas-group1
     */
    public String getGroupName() {
        return environment.getProperty("group");
    }

    /**
     * Retrieves the base URI of this auction house.
     *
     * @return the base URI of this auction house
     */
    public URI getAuctionHouseUri() {
        return URI.create(environment.getProperty("auction.house.uri"));
    }

    /**
     * Retrieves the URI of the TAPAS-Tasks task list of this TAPAS applicatoin. This is used, e.g.,
     * when placing a bid during the auction (see also {@link ch.unisg.tapas.auctionhouse.domain.Bid}).
     *
     * @return
     */
    public URI getTaskListUri() {
        return URI.create(environment.getProperty("tasks.list.uri"));
    }
}
