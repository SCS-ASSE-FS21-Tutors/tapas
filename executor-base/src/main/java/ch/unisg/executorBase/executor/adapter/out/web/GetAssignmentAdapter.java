package ch.unisg.executorBase.executor.adapter.out.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import ch.unisg.executorBase.executor.application.port.out.GetAssignmentPort;
import ch.unisg.executorBase.executor.domain.ExecutorType;
import ch.unisg.executorBase.executor.domain.Task;

import org.json.JSONObject;

@Component
@Primary
public class GetAssignmentAdapter implements GetAssignmentPort {

    //This is the base URI of the service interested in this event (in my setup, running locally as separate Spring Boot application)
    String server = "http://127.0.0.1:8082";

    @Override
    public Task getAssignment(ExecutorType executorType, String ip, int port) {

        String body = new JSONObject()
                  .put("executorType", executorType)
                  .put("ip", ip)
                  .put("port", port)
                  .toString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server+"/task/apply"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.body().equals("")) {
                return null;
            }

            return new Task(new JSONObject(response.body()).getString("taskID"));

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        }

        return null;
    }

}
