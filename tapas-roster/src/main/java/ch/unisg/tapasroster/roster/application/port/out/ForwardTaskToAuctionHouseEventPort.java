package ch.unisg.tapasroster.roster.application.port.out;

import ch.unisg.tapasroster.roster.domain.ForwardTaskToAuctionHouseEvent;

public interface ForwardTaskToAuctionHouseEventPort {
    void forwardTaskToAuctionHouseEvent(ForwardTaskToAuctionHouseEvent event);
}
