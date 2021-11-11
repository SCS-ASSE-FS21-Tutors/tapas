package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.application.port.out.AuctionWonEventPort;
import ch.unisg.tapas.auctionhouse.domain.AuctionWonEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * This class is a template for sending auction won events via HTTP. This class was created here only
 * as a placeholder, it is up to you to decide how such events should be sent (e.g., via HTTP,
 * WebSub, etc.).
 */
@Component
@Primary
public class AuctionWonEventHttpAdapter implements AuctionWonEventPort {

    private static final String PATH = "/taskwinner";

    @Override
    public void publishAuctionWonEvent(AuctionWonEvent event) {
        var winningBid = event.getWinningBid();


    }
}
