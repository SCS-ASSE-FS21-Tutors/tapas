package ch.unisg.tapastasks.tasks.adapter.out.web;

import ch.unisg.tapastasks.tasks.application.port.out.NewTaskAddedEventPort;
import ch.unisg.tapastasks.tasks.domain.NewTaskAddedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PublishNewTaskAddedEventWebAdapter implements NewTaskAddedEventPort {

    //This is the base URI of the service interested in this event (in my setup, running locally as separate Spring Boot application)
    String server = "http://127.0.0.1:8082";

    @Override
    public void publishNewTaskAddedEvent(NewTaskAddedEvent event) {

        //Here we would need to work with DTOs in case the payload of calls becomes more complex

        var values = new HashMap<String, String>() {{
            put("taskname",event.taskName);
            put("tasklist",event.taskListName);
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
                .uri(URI.create(server+"/roster/newtask/"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
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
    }
}
