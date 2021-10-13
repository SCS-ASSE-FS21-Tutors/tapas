package ch.unisg.tapasexecutorpool.executorpool.domain;

import lombok.Getter;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;


public class Roster {

    @Getter
    private final Roster.RosterName rosterName;

    @Getter
    private final ExecutorPool executorPool;

    @Getter
    private final ListOfNewTasks listOfNewTasks;

    private static final Roster roster = new Roster(new Roster.RosterName("tapas-roster-group2"));

    private Roster(Roster.RosterName rosterName) {
        this.rosterName = rosterName;
        this.executorPool = ExecutorPool.getTapasExecutorPool();
        this.listOfNewTasks = new ListOfNewTasks(new ArrayList<Task>());
    }

    public static Roster getTapasRoster() {
        return roster;
    }

    private Boolean assignTaskToInternalExecutor(Task task) {

        for (Executor executor : executorPool.getPoolOfExecutors().getValue()) {
            if (executor.getTaskType().getValue().equalsIgnoreCase(task.getTaskType().getValue())) {
                return assign(task, executor);
            }
        }
        return false;
    }

    private Boolean assign(Task task, Executor executor) {
        // TODO: Change task state

        // TODO: Change executor state


        return true;
    }

    public Task assignTask(Task task) {
        System.out.println("Task is ready to be assigned: " + task.getTaskName().getValue());

        return task;
    }

    @Value
    public static class RosterName {
        String value;
    }

    @Value
    public static class ListOfNewTasks {
        List<Task> value;
    }

}
