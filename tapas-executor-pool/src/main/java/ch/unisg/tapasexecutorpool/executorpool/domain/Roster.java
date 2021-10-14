package ch.unisg.tapasexecutorpool.executorpool.domain;

import lombok.Getter;
import lombok.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class Roster {

    @Getter
    private final RosterName rosterName;

    @Getter
    private final TaskAssignment taskAssignment;

    private static final Roster roster = new Roster(new Roster.RosterName("tapas-roster-group2"));

    private Roster(Roster.RosterName rosterName) {
        this.rosterName = rosterName;
        this.taskAssignment = new TaskAssignment(new HashMap<>());
    }

    public static Roster getTapasRoster() {
        return roster;
    }

    private Executor searchInternalExecutor(Task task) {
        List<Executor> executorsOfTaskType = ExecutorPool.getTapasExecutorPool().getExecutorsOfType(task.getTaskType());

        // TODO: More sophisticated assignment
        for (Executor executor : executorsOfTaskType) {
            if (executor.getExecutorState().getValue().equals(Executor.State.IDLE)) {
                return executor;
            }
        }
        return null;
    }

    private TaskAssignmentReply assign(Task task, Executor executor) {
        // TODO: Change task state

        // TODO: Change executor state

        taskAssignment.value.put(task.getTaskId(), executor);

        return new TaskAssignmentReply(executor.getExecutorName().getValue(), "internal");
    }

    public Optional<TaskAssignmentReply> assignTask(Task task) {
        System.out.println("Task is ready to be assigned: " + task.getTaskName().getValue());

        Executor internalExecutor = searchInternalExecutor(task);
        if (internalExecutor != null) {
            return Optional.of(assign(task, internalExecutor));
        }

        return Optional.empty();
    }

    @Value
    public static class RosterName {
        String value;
    }

    @Value
    public static class TaskAssignment {
        Map<Task.TaskId, Executor> value;
    }

}
