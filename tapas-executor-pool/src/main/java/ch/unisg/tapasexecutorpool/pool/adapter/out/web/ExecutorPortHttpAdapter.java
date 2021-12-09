package ch.unisg.tapasexecutorpool.pool.adapter.out.web;

import ch.unisg.tapasexecutorpool.common.formats.TaskJsonRepresentation;
import ch.unisg.tapasexecutorpool.pool.application.port.out.SendTaskToExecutorPort;
import ch.unisg.tapasexecutorpool.pool.domain.Executor;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Log
@Component
public class ExecutorPortHttpAdapter implements SendTaskToExecutorPort {

    private ObjectMapper om = new ObjectMapper();
    private HttpClient client = HttpClient.newHttpClient();

    public void sendTaskToExecutor(Task task, Executor executor) {

        // Calls the /start/ endpoint of the assigned executor
        String endpoint = executor.getExecutorUrl().getValue() + "/execute/";
        log.info("Sending task to Executor: " + endpoint);

        try {

            // Defines the request body
            var bodyStr = om.writeValueAsString(new TaskJsonRepresentation(task));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .headers("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(bodyStr))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 202) {
                // Set the state and assigned Task of the executor to occupied until notified of completion
                throw new RuntimeException("Executor responded with unexpected status code " + response.statusCode() + " instead of 202");
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not send task to executor", e);
        }
    }
}
