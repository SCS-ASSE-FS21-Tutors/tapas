package ch.unisg.executorBase.executor.adapter.out.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;

import ch.unisg.executorBase.executor.application.port.out.ExecutionFinishedEventPort;
import ch.unisg.executorBase.executor.domain.ExecutionFinishedEvent;

public class ExecutionFinishedEventAdapter implements ExecutionFinishedEventPort {

    //This is the base URI of the service interested in this event (in my setup, running locally as separate Spring Boot application)
    String server = "http://127.0.0.1:8082";

    @Override
    public void publishExecutionFinishedEvent(ExecutionFinishedEvent event) {

        String body = new JSONObject()
        .put("taskID", event.getTaskID())
        .put("result", event.getResult())
        .put("status", event.getStatus())
        .toString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server+"/task/"+event.getTaskID()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        }

        System.out.println("Finish execution event sent with result:" + event.getResult());

    }

}
