package ch.unisg.roster.roster.application.port.in;

import ch.unisg.roster.roster.domain.RosterItem;

import java.util.List;

public interface LoadRosterItemPort {

    RosterItem loadRosterItem(String taskId);

    List<RosterItem> loadAllRosterItems();
}
