package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapasexecutorpool.pool.application.port.in.AssignTaskCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.AssignTaskUseCase;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AssignTaskService implements AssignTaskUseCase {

    @Autowired
    public AssignTaskService(){

    }

    @Override
    public Executor assignTask(AssignTaskCommand command) {
        //TODO: Here we need to check the current repository for any available Executors


        Executor executor = new Executor(new Executor.ExecutorName("Test"),new Executor.ExecutorType("Testtype"),new Executor.ExecutorPort("8084"));
        System.out.println("Task is assigned to executor: "+ executor.getExecutorName());
        //TODO: Here we should call the "start" endpoint of the executor

        return executor;
    }
}
