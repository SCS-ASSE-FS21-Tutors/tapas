package ch.unisg.assignment.assignment.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Roster {

    private static final Roster roster = new Roster();

    private HashMap<String, ArrayList<Task>> queues = new HashMap<>();

    private HashMap<String, RosterItem> rosterMap = new HashMap<>();

    public static Roster getInstance() {
        return roster;
    }

    private Roster() {}

    public void addTaskToQueue(Task task) {
        if (queues.containsKey(task.getTaskType().toUpperCase())) {
            queues.get(task.getTaskType().toUpperCase()).add(task);
        } else {
            queues.put(task.getTaskType().toUpperCase(), new ArrayList<>(Arrays.asList(task)));
        }
    }

    public Task assignTaskToExecutor(String taskType, String executorIP, int executorPort) {
        if (!queues.containsKey(taskType.toUpperCase())) {
            return null;
        }
        if (queues.get(taskType.toUpperCase()).isEmpty()) {
            return null;
        }

        Task task = queues.get(taskType.toUpperCase()).remove(0);

        rosterMap.put(task.getTaskID(), new RosterItem(task.getTaskID(), task.getTaskType(),
            executorIP, executorPort));

        return task;
    }

    public void taskCompleted(String taskID) {
        rosterMap.remove(taskID);
    }

}
