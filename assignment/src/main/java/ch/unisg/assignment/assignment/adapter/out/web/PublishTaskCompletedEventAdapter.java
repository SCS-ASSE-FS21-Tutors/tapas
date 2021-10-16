package ch.unisg.assignment.assignment.adapter.out.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import ch.unisg.assignment.assignment.application.port.out.TaskCompletedEventPort;
import ch.unisg.assignment.assignment.domain.TaskCompletedEvent;

@Component
@Primary
public class PublishTaskCompletedEventAdapter implements TaskCompletedEventPort {

    String server = "http://127.0.0.1:8081";

    @Override
    public void publishTaskCompleted(TaskCompletedEvent event) {

        String body = new JSONObject()
                  .put("taskId", event.taskID)
                  .put("status", event.status)
                  .put("taskResult", event.result)
                  .toString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server + "/tasks/completeTask"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();


        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        }

    }

}
