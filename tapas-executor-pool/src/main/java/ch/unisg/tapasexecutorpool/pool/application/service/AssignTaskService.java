package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapasexecutorpool.pool.application.port.in.AssignTaskCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.AssignTaskUseCase;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log
@Component
public class AssignTaskService implements AssignTaskUseCase {

    @Autowired
    public ExecutorRepository repository;

    @Autowired
    public SendTaskToExecutorPort executorPort;

    @Autowired
    public AssignTaskService() {

    }


    @Override
    public Executor assignTask(AssignTaskCommand command) {

        Executor assignedExecutor = null;

        // Checks for the first available executor
        for (Executor executor : repository.getExecutors()) {
            if (executor.getExecutorState().getValue().equals(Executor.State.AVAILABLE)) {
                assignedExecutor = executor;
            }
        }

        // Thor error if not suitable executor is found
        if(assignedExecutor == null)
            throw new NoExecutorFoundException("No available executor found for TaskType=" + command.getTaskType().getValue());

        // Sending task to executor
        log.info("Assigned Executor: " + assignedExecutor.getExecutorName().getValue());
        executorPort.sendTaskToExecutor(command.getTaskId(), assignedExecutor);

        // Update executor
        assignedExecutor.setExecutorState(new Executor.ExecutorState(Executor.State.OCCUPIED));
        assignedExecutor.setAssignedTask(new Task(command.getTaskId(), command.getTaskName(), command.getTaskType()));
        repository.updateExecutor(assignedExecutor);

        // Return assigned executpr
        return assignedExecutor;
    }
}
