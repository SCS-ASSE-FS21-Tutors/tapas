package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.NotifyTaskCompletionCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.NotifyTaskCompletionUseCase;
import ch.unisg.tapasexecutorpool.pool.application.port.out.UpdateTaskStatusCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.out.UpdateTaskStatusCommandPort;
import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@Log4j2
public class NotifyTaskCompletionService implements NotifyTaskCompletionUseCase {

    @Autowired
    public ExecutorRepository repository;

    @Autowired
    public UpdateTaskStatusCommandPort updateTaskStatusCommandPort;


    @Override
    public void notifyTaskCompletion(NotifyTaskCompletionCommand command) {

        // Search for executor with corresponding executorID
        Optional<Executor> executorOptional = repository.findByTaskId(command.getTaskId());

        if(executorOptional.isPresent()){

            Executor executor = executorOptional.get();
            executor.setExecutorState(new Executor.ExecutorState(Executor.State.AVAILABLE));
            Task task = executor.getAssignedTask();
            task.setOutputData(new Task.OutputData(command.getOutputData()));

            executor.setAssignedTask(null);

            repository.updateExecutor(executor);

            log.info(executor.getExecutorId().getValue() + " completed its task");

            // Update task Status on task list
            UpdateTaskStatusCommand updateTaskStatusCommand = new UpdateTaskStatusCommand(task,new Task.TaskStatus(Task.Status.EXECUTED));
            updateTaskStatusCommandPort.updateTaskStatus(updateTaskStatusCommand);

        }
        else {
            throw new TaskNotBeingProcessedException("Executor for task completion of "+ command.getTaskId() + " not found");
        }

    }

}
