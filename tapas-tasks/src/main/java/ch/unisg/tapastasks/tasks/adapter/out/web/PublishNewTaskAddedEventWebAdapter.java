package ch.unisg.tapastasks.tasks.adapter.out.web;

import ch.unisg.tapastasks.tasks.application.port.out.NewTaskAddedEventPort;
import ch.unisg.tapastasks.tasks.domain.NewTaskAddedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

@Component
@Primary
public class PublishNewTaskAddedEventWebAdapter implements NewTaskAddedEventPort {

    //This is the base URI of the service interested in this event (in my setup, running locally as separate Spring Boot application)
    //    @org.springframework.beans.factory.annotation.Value("${newTaskEventServer}")
    //    private String server;
    String server = "http://127.0.0.1:8085";

    @Override
    public void publishNewTaskAddedEvent(NewTaskAddedEvent event) {

        //Here we would need to work with DTOs in case the payload of calls becomes more complex

        var values = new HashMap<String, String>() {{
            put("taskname", event.taskName);
            put("tasklist", event.taskListName);
            put("taskType", event.taskType);
            put("taskId", event.taskId);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = "";
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server+"/roster/newtask/"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();

         //Needs the other service running
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
