package ch.unisg.tapastasks.tasks.adapter.out.web;


import ch.unisg.tapastasks.tasks.application.port.out.CanTaskBeDeletedPort;
import ch.unisg.tapastasks.tasks.domain.DeleteTaskEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

@Component
@Primary
public class CanTaskBeDeletedWebAdapter implements CanTaskBeDeletedPort {

    @Value("${roster.uri}")
    String server;

    @Override
    public void canTaskBeDeletedEvent(DeleteTaskEvent event){

        var values = new HashMap<> () {{
            put("taskId", event.taskId);
            put("taskUri", event.taskUri);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

        //Todo: Question: How do we include the URI from the DeleteTaskEvent? Do we even need it?
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(server+"task"))
            .header("Content-Type", "application/task+json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

        //Todo: The following parameters probably need to be changed to get the right error code
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
