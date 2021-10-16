package ch.unisg.tapasexecutorpool.pool.application.service;

import ch.unisg.tapasexecutorpool.pool.application.port.in.AssignTaskCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.in.AssignTaskUseCase;
import ch.unisg.tapasexecutorpool.pool.application.port.repository.ExecutorRepository;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
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

import java.util.ArrayList;
import java.util.List;

@Component
public class AssignTaskService implements AssignTaskUseCase {

    @Autowired
    public ExecutorRepository repository;

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
        System.out.println("Assigned Executor: "+ assignedExecutor.toString());
        if (assignedExecutor != null) {
            // Calls the /start/ endpoint of the assigned executor
            String endpoint = assignedExecutor.getExecutorUrl().getValue() + "/start/";
            CloseableHttpClient httpclient = HttpClients.createDefault();

            // Defines the request body
            JSONArray values = new JSONArray();
            values.put(1);
            values.put(2);

            JSONObject json = new JSONObject();
            json.put("taskID", command.getTaskId().getValue());
            json.put("values", values);

            StringEntity requestEntity = new StringEntity(
                    json.toString(),
                    ContentType.APPLICATION_JSON);

            // Executes request
            HttpPost postMethod = new HttpPost(endpoint);
            postMethod.setEntity(requestEntity);
            try {
                HttpResponse rawResponse = httpclient.execute(postMethod);
                System.out.println(rawResponse);
            } catch (Exception e){
                System.out.println(e);
            }

            // Set the state and assigned Task of the executor to occupied until notified of completion
            assignedExecutor.setExecutorState(new Executor.ExecutorState(Executor.State.OCCUPIED));
            assignedExecutor.setAssignedTask(new Task(command.getTaskId(),command.getTaskName(),command.getTaskType()));
        }
        return assignedExecutor;
    }
}
