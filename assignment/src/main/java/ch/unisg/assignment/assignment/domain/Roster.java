package ch.unisg.assignment.assignment.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import ch.unisg.assignment.assignment.domain.valueobject.ExecutorType;
import ch.unisg.common.valueobject.ExecutorURI;

public class Roster {

    private static final Roster roster = new Roster();

    private HashMap<String, ArrayList<Task>> queues = new HashMap<>();

    private HashMap<String, RosterItem> rosterMap = new HashMap<>();

    public static Roster getInstance() {
        return roster;
    }

    private Roster() {}

    public void addTaskToQueue(Task task) {
        if (queues.containsKey(task.getTaskType().getValue())) {
            queues.get(task.getTaskType().getValue()).add(task);
        } else {
            queues.put(task.getTaskType().getValue(), new ArrayList<>(Arrays.asList(task)));
        }
    }

    public Task assignTaskToExecutor(ExecutorType taskType, ExecutorURI executorURI) {
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

    public void taskCompleted(String taskID) {
        rosterMap.remove(taskID);
    }

}
