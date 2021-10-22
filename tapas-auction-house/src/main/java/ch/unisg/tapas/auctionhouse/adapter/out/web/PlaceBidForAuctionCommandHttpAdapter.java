package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.application.port.out.PlaceBidForAuctionCommand;
import ch.unisg.tapas.auctionhouse.application.port.out.PlaceBidForAuctionCommandPort;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Sends the place bid for an auction command via HTTP.
 */
@Component
@Primary
public class PlaceBidForAuctionCommandHttpAdapter implements PlaceBidForAuctionCommandPort {

    @Override
    public void placeBid(PlaceBidForAuctionCommand command) {
        // TODO
    }
}
