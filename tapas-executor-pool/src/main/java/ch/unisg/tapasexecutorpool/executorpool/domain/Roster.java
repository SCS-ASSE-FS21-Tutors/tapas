package ch.unisg.tapasexecutorpool.executorpool.domain;

import lombok.Getter;
import lombok.Value;


public class Roster {

    @Getter
    private final Roster.RosterName rosterName;

    @Getter
    private final ExecutorPool executorPool;

    private static final Roster roster = new Roster(new Roster.RosterName("tapas-roster-group2"));

    private Roster(Roster.RosterName rosterName) {
        this.rosterName = rosterName;
        this.executorPool = ExecutorPool.getTapasExecutorPool();
    }

    public static Roster getTapasRoster() {
        return roster;
    }

    public void assignTaskToInternalExecutor() {

    }

    @Value
    public static class RosterName {
        String value;
    }

}
