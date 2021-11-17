package ch.unisg.tapas.auctionhouse.application.handler;

import ch.unisg.tapas.auctionhouse.application.port.in.AuctionStartedEvent;
import ch.unisg.tapas.auctionhouse.application.port.in.AuctionStartedEventHandler;
import ch.unisg.tapas.auctionhouse.application.port.out.PlaceBidForAuctionCommand;
import ch.unisg.tapas.auctionhouse.application.port.out.PlaceBidForAuctionCommandPort;
import ch.unisg.tapas.auctionhouse.application.port.out.QueryAvailableExecutorsFromPoolEvent;
import ch.unisg.tapas.auctionhouse.application.port.out.QueryAvailableExecutorsFromPoolEventHandler;
import ch.unisg.tapas.auctionhouse.domain.Bid;
import ch.unisg.tapas.auctionhouse.domain.ExecutorRegistry;
import ch.unisg.tapas.common.ConfigProperties;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Handler for auction started events. This handler will automatically bid in any auction for a
 * task of known type, i.e. a task for which the auction house knows an executor is available.
 */
@RequiredArgsConstructor
@Component
public class AuctionStartedHandler implements AuctionStartedEventHandler {
    private static final Logger LOGGER = LogManager.getLogger(AuctionStartedHandler.class);

    private final ConfigProperties config;
    private final PlaceBidForAuctionCommandPort placeBidForAuctionCommandPort;
    private final QueryAvailableExecutorsFromPoolEventHandler queryAvailableExecutorsFromPoolEventHandler;

    /**
     * Handles an auction started event and bids in all auctions for tasks of known types.
     *
     * @param auctionStartedEvent the auction started domain event
     * @return true unless a runtime exception occurs
     */
    @Override
    public boolean handleAuctionStartedEvent(AuctionStartedEvent auctionStartedEvent) {
        var auction = auctionStartedEvent.getAuctionJsonRepresentation().deserialize();

        var event = new QueryAvailableExecutorsFromPoolEvent();
        queryAvailableExecutorsFromPoolEventHandler.retrieveAvailableExecutors(event);

        if (ExecutorRegistry.getInstance().hasExecutorWithAuctionType(auction.getTaskType().getValue())) {
            LOGGER.info("Placing bid for task " + auction.getTaskUri() + " of type "
                + auction.getTaskType() + " in auction " + auction.getAuctionId()
                + " from auction house " + auction.getAuctionHouseUri().getValue().toString());

            Bid bid = new Bid(auction.getAuctionId(),
                new Bid.BidderName(config.getGroupName()),
                new Bid.BidderAuctionHouseUri(config.getAuctionHouseUri()),
                new Bid.BidderTaskListUri(config.getTaskListUri())
            );

            PlaceBidForAuctionCommand command = new PlaceBidForAuctionCommand(auction, bid);
            placeBidForAuctionCommandPort.placeBid(command);
        } else {
            LOGGER.info("Cannot execute this task type: " + auction.getTaskType().getValue());
        }

        return true;
    }
}
