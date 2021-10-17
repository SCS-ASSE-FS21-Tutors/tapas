package ch.unisg.assignment.assignment.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import ch.unisg.assignment.assignment.domain.valueobject.ExecutorType;
import ch.unisg.assignment.assignment.domain.valueobject.IP4Adress;
import ch.unisg.assignment.assignment.domain.valueobject.Port;

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

    public Task assignTaskToExecutor(ExecutorType taskType, IP4Adress executorIP, Port executorPort) {
        if (!queues.containsKey(taskType.getValue())) {
            return null;
        }
        if (queues.get(taskType.getValue()).isEmpty()) {
            return null;
        }

        Task task = queues.get(taskType.getValue()).remove(0);

        rosterMap.put(task.getTaskID(), new RosterItem(task.getTaskID(),
            task.getTaskType().getValue(), executorIP, executorPort));

        return task;
    }

    public void taskCompleted(String taskID) {
        rosterMap.remove(taskID);
    }

}
