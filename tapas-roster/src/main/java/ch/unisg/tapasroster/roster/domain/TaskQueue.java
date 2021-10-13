package ch.unisg.tapasroster.roster.domain;

import lombok.Getter;
import lombok.Value;
import lombok.val;

import java.util.ArrayList;
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
        ArrayList<String> allIds = new ArrayList<String>();
        for(Task t:this.queueOfTasks.value){
            allIds.add(t.getTaskId().getValue());
        }
        if(!(allIds.contains(task.getTaskId().getValue()))){
            this.queueOfTasks.value.add(task);
        }

    }

    public Task retrieveNextTask(){
        Task nextTask = this.queueOfTasks.value.getFirst();
        this.queueOfTasks.value.removeFirst();
        return nextTask;
    }

    @Value
    public static class QueueOfTasks {
        private LinkedList<Task> value;
    }
}
