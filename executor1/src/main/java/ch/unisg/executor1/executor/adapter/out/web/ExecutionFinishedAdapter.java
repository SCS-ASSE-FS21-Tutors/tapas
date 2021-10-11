package ch.unisg.executor1.executor.adapter.out.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import ch.unisg.executor1.executor.application.port.out.ExecutionFinishedEventPort;
import ch.unisg.executor1.executor.domain.ExecutionFinishedEvent;

public class ExecutionFinishedAdapter implements ExecutionFinishedEventPort {

    //This is the base URI of the service interested in this event (in my setup, running locally as separate Spring Boot application)
    String server = "http://127.0.0.1:8082";

    @Override
    public void publishExecutionFinishedEvent(ExecutionFinishedEvent event) {
        ///Here we would need to work with DTOs in case the payload of calls becomes more complex

        var values = new HashMap<String, String>() {{
            put("result",event.getResult());
            put("status",event.getStatus());
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server+"/task/"+event.getTaskID()))
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        /** Needs the other service running
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         **/

        System.out.println("Finish execution event sent");
        
    }
    
}
