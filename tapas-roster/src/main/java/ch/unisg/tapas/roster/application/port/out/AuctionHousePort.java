package ch.unisg.tapas.roster.application.port.out;

import ch.unisg.tapas.roster.entities.Task;

public interface AuctionHousePort {

    void executeExternally(Task task);
}
