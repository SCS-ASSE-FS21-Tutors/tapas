package ch.unisg.tapasroster.roster.domain;

import lombok.Getter;
import lombok.Value;

import java.util.LinkedList;

public class TaskQueue {

    @Getter
    private final QueueOfTasks queueOfTasks;

    //Singleton Implementation
    private static final TaskQueue taskQueue = new TaskQueue();
    private TaskQueue() {
        this.queueOfTasks = new QueueOfTasks(new LinkedList<Task>());
    }

    public void addTaskToQueue(Task task){
        this.queueOfTasks.value.add(task);
    }

    public Task getNextTask(){
        return this.queueOfTasks.value.getFirst();
    }

    @Value
    public static class QueueOfTasks {
        private LinkedList<Task> value;
    }
}
