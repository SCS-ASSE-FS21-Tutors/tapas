package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.NotifyTaskCompletionCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.NotifyTaskCompletionUseCase;
import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class NotifyTaskCompletionService implements NotifyTaskCompletionUseCase {

    @Autowired
    public ExecutorRepository repository;

    @Override
    public void notifyTaskCompletion(NotifyTaskCompletionCommand command) {

        // Search for executor with corresponding executorID
        Executor executor = findByTaskId(repository.getExecutors(), command.getTaskId());


        // Change state of executor
        if(executor!=null) {
            executor.setExecutorState(new Executor.ExecutorState(Executor.State.AVAILABLE));
            executor.setAssignedTask(null);

            System.out.println(executor.getExecutorName().getValue() + " completed its task");
        }else {
            System.out.println("Executor for task completion of "+ command.getTaskId() + " not found");
        }

    }

    public static Executor findByTaskId(Collection<Executor> executors, String taskId) {
        return executors.stream().filter(executor -> taskId.equals(executor.getAssignedTask().getTaskId().getValue())).findFirst().orElse(null);
    }

}
