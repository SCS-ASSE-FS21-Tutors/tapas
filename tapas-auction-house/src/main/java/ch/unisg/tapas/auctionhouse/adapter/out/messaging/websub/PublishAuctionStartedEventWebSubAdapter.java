package ch.unisg.tapas.auctionhouse.adapter.out.messaging.websub;

import ch.unisg.tapas.auctionhouse.adapter.common.clients.WebSubPublisher;
import ch.unisg.tapas.auctionhouse.application.port.out.AuctionStartedEventPort;
import ch.unisg.tapas.auctionhouse.domain.AuctionStartedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Primary
public class PublishAuctionStartedEventWebSubAdapter implements AuctionStartedEventPort {

    private final WebSubPublisher webSubPublisher;

    @Override
    public void publishAuctionStartedEvent(AuctionStartedEvent event) {
        webSubPublisher.notifyHub("/auctions/");
    }
}
