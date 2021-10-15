package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToExecutorPoolUseCase;
import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddNewExecutorService implements AddNewExecutorToExecutorPoolUseCase {

    public ExecutorRepository repository;

    @Autowired
    public AddNewExecutorService(ExecutorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Executor addNewExecutorToExecutorPool(AddNewExecutorToExecutorPoolCommand command) {

        Executor executor = new Executor(command.getExecutorName(), command.getExecutorType(), null);
        repository.addExecutor(executor);
        System.out.println("Current Executor Repository: "+ repository.getExecutors());
        return executor;
    }


}
