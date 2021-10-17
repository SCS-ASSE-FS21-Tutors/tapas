package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.NotifyTaskCompletionCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.NotifyTaskCompletionUseCase;
import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Log
@Component
public class NotifyTaskCompletionService implements NotifyTaskCompletionUseCase {

    @Autowired
    public ExecutorRepository repository;

    @Override
    public void notifyTaskCompletion(NotifyTaskCompletionCommand command) {

        // Search for executor with corresponding executorID
        Optional<Executor> executorOptional = repository.findByTaskId(command.getTaskId());

        if(executorOptional.isPresent()){

            Executor executor = executorOptional.get();
            executor.setExecutorState(new Executor.ExecutorState(Executor.State.AVAILABLE));
            executor.setAssignedTask(null);

            repository.updateExecutor(executor);

            System.out.println(executor.getExecutorId().getValue() + " completed its task");
        }
        else {
            throw new TaskNotBeingProcessedException("Executor for task completion of "+ command.getTaskId() + " not found");
        }

    }

}
