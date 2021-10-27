package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapasexecutorpool.pool.application.port.in.AssignTaskCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.AssignTaskUseCase;
import ch.unisg.tapasexecutorpool.pool.application.port.in.CanExecuteTaskQuery;
import ch.unisg.tapasexecutorpool.pool.application.port.in.EnqueueTaskUseCase;
import ch.unisg.tapasexecutorpool.pool.application.port.out.SendTaskToExecutorPort;
import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
import lombok.extern.java.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@Log
@Component
public class AssignTaskService implements CanExecuteTaskQuery, EnqueueTaskUseCase {

    @Autowired
    public ExecutorRepository repository;

    @Autowired
    public SendTaskToExecutorPort executorPort;

    public LinkedList<Task> taskQueue = new LinkedList<>();

    @Autowired
    public AssignTaskService() {

    }

    /**
     * Check if we have an executor that implements the task type
     * @param task
     * @return true if we can execute this task
     */
    @Override
    public boolean canExecute(Task task) {

        var executors = repository.getExecutors();
        return executors.stream()
                .anyMatch(e -> e.getExecutorType().getValue().equals(task.getTaskType().getValue()));
    }

    @Override
    public void enqueueTask(Task task) {

        if(!canExecute(task))
            throw new NoExecutorFoundException("cannot execute task");

        taskQueue.add(task);
    }

    @Scheduled(fixedRate = 5000)
    public void scheduleFixedRateTask() {

        log.info("Checking task queue: " + taskQueue.toString());

        for (Task task : taskQueue) {

            if(canExecuteNow(task)){

                taskQueue.remove(task);

                try{
                    assignTask(task);
                }
                catch (Exception ex){
                    // Read to task queue if failed
                    taskQueue.push(task);
                    log.warning("Failed to assign Task");
                    throw new RuntimeException("Failed to assign Task", ex);
                }
            }
        }
    }

    private boolean canExecuteNow(Task task){

        var executors = repository.getExecutors();
        var canExecuteNow = executors.stream()
                .filter(e -> e.getExecutorType().getValue().equals(task.getTaskType().getValue()))
                .anyMatch(e -> e.getExecutorState().getValue().equals(Executor.State.AVAILABLE));

        return canExecuteNow;
    }

    private Executor assignTask(Task task) {

        log.info("Assigning Task: " + task.toString());

        if(! canExecuteNow(task))
            throw new RuntimeException("Can not execute task now");

        Executor assignedExecutor = null;

        // Checks for the first available executor
        for (Executor executor : repository.getExecutors()) {
            if (executor.getExecutorState().getValue().equals(Executor.State.AVAILABLE)) {
                assignedExecutor = executor;
            }
        }

        // Thor error if not suitable executor is found
        if(assignedExecutor == null)
            throw new NoExecutorFoundException("No available executor found for TaskType=" + task.getTaskType().getValue());

        // Sending task to executor
        log.info("Assigned Executor: " + assignedExecutor.getExecutorName().getValue());
        executorPort.sendTaskToExecutor(task.getTaskId(), assignedExecutor);

        // Update executor
        assignedExecutor.setExecutorState(new Executor.ExecutorState(Executor.State.OCCUPIED));
        assignedExecutor.setAssignedTask(new Task(task.getTaskId(), task.getTaskName(), task.getTaskType()));
        repository.updateExecutor(assignedExecutor);

        // Return assigned executor
        return assignedExecutor;
    }
}
