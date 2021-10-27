package ch.unisg.executorbase.executor.adapter.out.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import ch.unisg.executorbase.executor.application.port.out.ExecutionFinishedEventPort;
import ch.unisg.executorbase.executor.domain.ExecutionFinishedEvent;

public class ExecutionFinishedEventAdapter implements ExecutionFinishedEventPort {

    @Value("${roster.url}")
    String server;

    Logger logger = Logger.getLogger(ExecutionFinishedEventAdapter.class.getName());

    @Override
    public void publishExecutionFinishedEvent(ExecutionFinishedEvent event) {

        String body = new JSONObject()
        .put("taskID", event.getTaskID())
        .put("result", event.getResult())
        .put("status", event.getStatus())
        .toString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server+"/task/completed"))
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

        System.out.println("Finish execution event sent with result:" + event.getResult());

    }

}
