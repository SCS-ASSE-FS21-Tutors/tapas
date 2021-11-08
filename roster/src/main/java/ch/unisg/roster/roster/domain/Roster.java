package ch.unisg.roster.roster.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.unisg.roster.roster.domain.valueobject.ExecutorType;
import ch.unisg.common.valueobject.ExecutorURI;

public class Roster {

    private static final Roster roster = new Roster();

    // Queues which hold all the tasks which need to be assigned | Will be replaced by message queue later
    private HashMap<String, ArrayList<Task>> queues = new HashMap<>();

    // Roster witch holds information about which executor is assigned to a task
    private HashMap<String, RosterItem> rosterMap = new HashMap<>();

    Logger logger = Logger.getLogger(Roster.class.getName());

    public static Roster getInstance() {
        return roster;
    }

    private Roster() {}

    /**
    *   Adds a task to the task queue.
    *   @return void
    *   @see Task
    **/
    public void addTaskToQueue(Task task) {
        if (queues.containsKey(task.getTaskType().getValue())) {
            queues.get(task.getTaskType().getValue()).add(task);
        } else {
            queues.put(task.getTaskType().getValue(), new ArrayList<>(Arrays.asList(task)));
        }
        logger.log(Level.INFO, "Added task with id {0} to queue", task.getTaskID());
    }

    /**
    *   Checks if a task of this type is in a queue and if so assignes it to the executor.
    *   @return assigned task or null if no task is found
    *   @see Task
    **/
    public Task assignTaskToExecutor(ExecutorType taskType, ExecutorURI executorURI) {
        // TODO I don't think we need this if
        if (!queues.containsKey(taskType.getValue())) {
            return null;
        }
        if (queues.get(taskType.getValue()).isEmpty()) {
            return null;
        }

        Task task = queues.get(taskType.getValue()).remove(0);

        rosterMap.put(task.getTaskID(), new RosterItem(task.getTaskID(),
            task.getTaskType().getValue(), executorURI));

        return task;
    }

    /**
    *   Removed a task from the roster after if got completed
    *   @return void
    *   @see Task
    *   @see Roster
    **/
    public void taskCompleted(String taskID) {
        rosterMap.remove(taskID);
        logger.log(Level.INFO, "Task {0} completed", taskID);
    }

    /**
    *   Deletes a task if it is still in the queue.
    *   @return Whether the task got deleted or not
    **/
    public boolean deleteTask(String taskID, ExecutorType taskType) {
        logger.log(Level.INFO, "Try to delete task with id {0}", taskID);
        return queues.get(taskType.getValue()).removeIf(task -> task.getTaskID().equalsIgnoreCase(taskID));
    }

}
