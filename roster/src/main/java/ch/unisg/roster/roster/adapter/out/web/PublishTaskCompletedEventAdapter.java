package ch.unisg.roster.roster.adapter.out.web;

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

import ch.unisg.roster.roster.application.port.out.TaskCompletedEventPort;
import ch.unisg.roster.roster.domain.event.TaskCompletedEvent;

@Component
@Primary
public class PublishTaskCompletedEventAdapter implements TaskCompletedEventPort {

    @Value("${task-list.url}")
    private String server;

    Logger logger = Logger.getLogger(PublishTaskCompletedEventAdapter.class.getName());

    /**
    *   Informs the task service about the completion of the task.
    *   @return void
    **/
    @Override
    public void publishTaskCompleted(TaskCompletedEvent event) {

        System.out.println("PublishTaskCompletedEventAdapter.publishTaskCompleted()");
        System.out.print(server);

        String body = new JSONObject()
                  .put("taskId", event.taskID)
                  .put("status", event.status)
                  .put("outputData", event.result)
                  .toString();

        System.out.println(event.taskID);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server + "/tasks/completeTask/" + event.taskID))
                .header("Content-Type", "application/task+json")
                .GET()
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
