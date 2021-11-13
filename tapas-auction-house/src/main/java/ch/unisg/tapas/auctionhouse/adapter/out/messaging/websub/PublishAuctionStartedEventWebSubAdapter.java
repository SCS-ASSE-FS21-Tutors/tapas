package ch.unisg.tapas.auctionhouse.adapter.out.messaging.websub;

import ch.unisg.tapas.auctionhouse.application.port.out.AuctionStartedEventPort;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.AuctionStartedEvent;
import ch.unisg.tapas.common.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is a template for publishing auction started events via WebSub.
 */
@Component
public class PublishAuctionStartedEventWebSubAdapter implements AuctionStartedEventPort {
    // You can use this object to retrieve properties from application.properties, e.g. the
    // WebSub hub publish endpoint, etc.
    @Autowired
    private ConfigProperties config;

    @Override
    public void publishAuctionStartedEvent(AuctionStartedEvent event) {
        // TODO
    }
}
