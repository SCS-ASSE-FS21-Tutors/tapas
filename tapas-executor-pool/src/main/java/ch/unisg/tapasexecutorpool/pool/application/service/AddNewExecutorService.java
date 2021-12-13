package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.AddNewExecutorToExecutorPoolUseCase;
import ch.unisg.tapasexecutorpool.pool.application.port.in.ListExecutorsQuery;
import ch.unisg.tapasexecutorpool.pool.application.port.out.AddExecutorPort;
import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Log
@Component
public class AddNewExecutorService implements AddNewExecutorToExecutorPoolUseCase, ListExecutorsQuery {

    public ExecutorRepository repository;

    private AddExecutorPort addExecutorPort;

    @Autowired
    public AddNewExecutorService(ExecutorRepository repository, AddExecutorPort addExecutorPort) {
        this.repository = repository;
        this.addExecutorPort = addExecutorPort;
    }

    @Override
    public Executor addNewExecutorToExecutorPool(AddNewExecutorToExecutorPoolCommand command) {

        Executor executor = new Executor(command.getExecutorName(), command.getExecutorType(), command.getExecutorUrl());

        // Add to persistent DB
        addExecutorPort.addExecutor(executor);
        // Add to operational internal repository
        repository.addExecutor(executor);
        log.info("Executor added: " + executor.toString());

        return executor;
    }

    @Override
    public Collection<Executor> listExecutors(){

        return repository.getExecutors();
    }


}
