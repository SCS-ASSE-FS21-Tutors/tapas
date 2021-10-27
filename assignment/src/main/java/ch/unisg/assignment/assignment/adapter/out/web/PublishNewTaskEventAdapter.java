package ch.unisg.assignment.assignment.adapter.out.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import ch.unisg.assignment.assignment.application.port.out.NewTaskEventPort;
import ch.unisg.assignment.assignment.domain.event.NewTaskEvent;

@Component
@Primary
public class PublishNewTaskEventAdapter implements NewTaskEventPort {

    @Value("${executor1.url}")
    private String server;

    @Value("${executor2.url}")
    private String server2;

    Logger logger = Logger.getLogger(PublishNewTaskEventAdapter.class.getName());

    @Override
    public void publishNewTaskEvent(NewTaskEvent event) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server + "/newtask/" + event.taskType.getValue()))
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

        HttpClient client2 = HttpClient.newHttpClient();
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create(server2 + "/newtask/" + event.taskType.getValue()))
                .GET()
                .build();


        try {
            client2.send(request2, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

}
