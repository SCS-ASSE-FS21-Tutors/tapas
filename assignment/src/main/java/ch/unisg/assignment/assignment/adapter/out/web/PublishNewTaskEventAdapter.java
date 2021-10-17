package ch.unisg.assignment.assignment.adapter.out.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import ch.unisg.assignment.assignment.application.port.out.NewTaskEventPort;
import ch.unisg.assignment.assignment.domain.event.NewTaskEvent;

@Component
@Primary
public class PublishNewTaskEventAdapter implements NewTaskEventPort {

    String server = "http://127.0.0.1:8085";

    @Override
    public void publishNewTaskEvent(NewTaskEvent event) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server + "/newtask/" + event.taskType.getValue()))
                .GET()
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
