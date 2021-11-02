package ch.unisg.assignment.assignment.adapter.out.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import ch.unisg.assignment.assignment.application.port.out.TaskAssignedEventPort;
import ch.unisg.assignment.assignment.domain.event.TaskAssignedEvent;

@Component
@Primary
public class PublishTaskAssignedEventAdapter implements TaskAssignedEventPort {

    @Value("${task-list.url}")
    private String server;

    Logger logger = Logger.getLogger(PublishTaskAssignedEventAdapter.class.getName());

    /**
    *   Informs the task service about the assignment of the task.
    *   @return void
    **/
    @Override
    public void publishTaskAssignedEvent(TaskAssignedEvent event) {

        String body = new JSONObject()
                  .put("taskId", event.taskID)
                  .toString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server + "/tasks/assignTask"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();


        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

}
