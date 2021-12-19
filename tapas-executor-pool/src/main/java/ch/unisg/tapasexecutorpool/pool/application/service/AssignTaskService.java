package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapasexecutorpool.pool.application.port.in.CanExecuteTaskQuery;
import ch.unisg.tapasexecutorpool.pool.application.port.in.EnqueueTaskUseCase;
import ch.unisg.tapasexecutorpool.pool.application.port.out.SendTaskToExecutorPort;
import ch.unisg.tapasexecutorpool.pool.application.port.out.UpdateTaskStatusCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.out.UpdateTaskStatusCommandPort;
import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

@Log
@Component
public class AssignTaskService implements CanExecuteTaskQuery, EnqueueTaskUseCase {

    @Autowired
    public ExecutorRepository repository;

    @Autowired
    public SendTaskToExecutorPort executorPort;

    @Autowired
    public UpdateTaskStatusCommandPort updateTaskStatusCommandPort;

    public BlockingQueue<Task> taskQueue;

    @Autowired
    public AssignTaskService(ExecutorRepository repository, SendTaskToExecutorPort executorPort, UpdateTaskStatusCommandPort updateTaskStatusCommandPort) {
        this.repository = repository;
        this.executorPort = executorPort;
        this.updateTaskStatusCommandPort = updateTaskStatusCommandPort;
        this.taskQueue = new LinkedBlockingQueue<>();
    }

    /**
     * Check if we have an executor that implements the task type
     *
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

        if (!canExecute(task))
            throw new NoExecutorFoundException("cannot execute task");

        taskQueue.add(task);
    }

    @Scheduled(fixedRate = 5000)
    public void scheduleFixedRateTask() {

        for (Task task : taskQueue) {
            int retries = 3;
            if (canExecuteNow(task)) {
                taskQueue.remove(task);
                try {
                    assignTask(task);
                } catch (Exception ex) {
                    // Read to task queue if failed
                    // Could implement a retry mechanism here
                    taskQueue.offer(task);
                    log.warning("Failed to assign Task");
                    throw new RuntimeException("Failed to assign Task", ex);
                }
            }
        }
    }

    private boolean canExecuteNow(Task task) {

        var executors = repository.getExecutors();
        var canExecuteNow = executors.stream()
                .filter(e -> e.getExecutorType().getValue().equals(task.getTaskType().getValue()))
                .anyMatch(e -> e.getExecutorState().getValue().equals(Executor.State.AVAILABLE));

        return canExecuteNow;
    }

    private Executor assignTask(Task task) {

        log.info("Assigning Task: " + task.getTaskId().getValue());

        if (!canExecuteNow(task))
            throw new RuntimeException("Can not execute task now");

        Executor assignedExecutor = null;

        // Checks for the first available executor
        var suitableExecutors = repository.getExecutors().stream()
                .filter(e -> e.getExecutorType().getValue().equals(task.getTaskType().getValue()))
                .collect(Collectors.toList());

        for (Executor executor : suitableExecutors) {
            if (executor.getExecutorState().getValue().equals(Executor.State.AVAILABLE)) {
                assignedExecutor = executor;
            }
        }

        // Thor error if not suitable executor is found
        if (assignedExecutor == null)
            throw new NoExecutorFoundException("No available executor found for TaskType=" + task.getTaskType().getValue());

        // Sending task to executor
        log.info("Assigned Executor: " + assignedExecutor.getExecutorName().getValue());
        executorPort.sendTaskToExecutor(task, assignedExecutor);

        // Update executor
        assignedExecutor.setExecutorState(new Executor.ExecutorState(Executor.State.OCCUPIED));
        assignedExecutor.setAssignedTask(task);
        repository.updateExecutor(assignedExecutor);

        // Update task in task list service
        UpdateTaskStatusCommand updateTaskStatusCommand = new UpdateTaskStatusCommand(task, new Task.TaskStatus(Task.Status.ASSIGNED));
        updateTaskStatusCommandPort.updateTaskStatus(updateTaskStatusCommand);

        // Return assigned executor
        return assignedExecutor;
    }
}
