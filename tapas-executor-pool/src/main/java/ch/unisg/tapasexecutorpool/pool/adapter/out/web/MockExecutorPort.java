package ch.unisg.tapasexecutorpool.pool.adapter.out.web;

import ch.unisg.tapasexecutorpool.pool.application.port.in.AssignTaskCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.out.SendTaskToExecutorPort;
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
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log
@Component
public class MockExecutorPort implements SendTaskToExecutorPort {

    public void sendTaskToExecutor(Task.TaskId taskId, Executor executor) {

        // Calls the /start/ endpoint of the assigned executor
        String endpoint = executor.getExecutorUrl().getValue() + "/start/";
        CloseableHttpClient httpclient = HttpClients.createDefault();

        log.info("MOCK Sending task to: " + endpoint);

        // Defines the request body
        JSONArray values = new JSONArray();
        values.put(1);
        values.put(2);

        JSONObject json = new JSONObject();
        json.put("taskID", taskId.getValue());
        json.put("values", values);

        StringEntity requestEntity = new StringEntity(
                json.toString(),
                ContentType.APPLICATION_JSON);

        /*
        // Executes request
        HttpPost postMethod = new HttpPost(endpoint);
        postMethod.setEntity(requestEntity);
        try {
            HttpResponse rawResponse = httpclient.execute(postMethod);
            // Check if request is accepted by executor
            if (rawResponse.getStatusLine().getStatusCode() == 200) {
                // Set the state and assigned Task of the executor to occupied until notified of completion
                throw new RuntimeException("Executor responded with unexpected status code " + rawResponse.getStatusLine().getStatusCode() + " instead of 200");
            } else {
                throw new IOException("Executor did not accept request");
            }
        } catch (Exception e) {

            throw new RuntimeException("Could not send task to executor", e);
        }
        */
    }
}
