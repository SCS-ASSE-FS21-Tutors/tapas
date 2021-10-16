package ch.unisg.assignment.assignment.adapter.out.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import ch.unisg.assignment.assignment.application.port.out.TaskAssignedEventPort;
import ch.unisg.assignment.assignment.domain.TaskAssignedEvent;

@Component
@Primary
public class PublishTaskAssignedEventAdapter implements TaskAssignedEventPort {

    String server = "http://127.0.0.1:8085";

    @Override
    public void publishTaskAssignedEvent(TaskAssignedEvent event) {

        String body = new JSONObject()
                  .put("taskId", event.taskID)
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
