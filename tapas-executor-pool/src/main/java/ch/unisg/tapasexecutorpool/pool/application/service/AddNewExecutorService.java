package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToExecutorPoolUseCase;
import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddNewExecutorService implements AddNewExecutorToExecutorPoolUseCase {

    private ExecutorRepository repository;

    @Autowired
    public AddNewExecutorService(ExecutorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Executor addNewExecutorToExecutorPool(AddNewExecutorToExecutorPoolCommand command) {

        Executor executor = new Executor(command.getExecutorName(), command.getExecutorType(), null);
        repository.addExecutor(executor);
        return executor;
    }
}
