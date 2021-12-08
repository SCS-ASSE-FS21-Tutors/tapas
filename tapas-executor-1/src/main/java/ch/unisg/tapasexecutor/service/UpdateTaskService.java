package ch.unisg.tapasexecutor.service;

import ch.unisg.tapasexecutor.domain.Task;
import ch.unisg.tapasexecutor.web.TaskJsonRepresentation;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Log4j2
@Component
public class UpdateTaskService {

    private HttpClient client = HttpClient.newHttpClient();
    private ObjectMapper om = new ObjectMapper();
    private URI completionEndpoint;

    public UpdateTaskService(@Value("${ch.tapas.executor-1.executor-pool-url}") String executorPoolUrl) {

        completionEndpoint = URI.create(executorPoolUrl + "/completion");
    }

    public void setTaskComplete(Task task) throws Exception {

        // Assemble request
        var bodyString = om.writeValueAsString(new TaskJsonRepresentation(task));


        HttpRequest request = HttpRequest.newBuilder()
                .uri(completionEndpoint)
                .method("PUT", HttpRequest.BodyPublishers.ofString(bodyString))
                .header("Content-Type", "application/json-patch+json")
                .build();

        // Send request
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Log response
        int statusCode = response.statusCode();
        log.info("Http Request: PUT " + completionEndpoint.toString() + " " + statusCode + " " + bodyString);

        if(statusCode != 200){

            throw new RuntimeException("Unexpected response status code: " + statusCode);
        }
    }
}
