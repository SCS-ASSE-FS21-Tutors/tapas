package ch.unisg.tapas.tapasexecutor2.application;

import ch.unisg.tapas.tapasexecutor2.web.TaskJsonRepresentation;
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
public class UpdateTaskHttpAdapter {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper om = new ObjectMapper();
    private final URI completionEndpoint;

    public UpdateTaskHttpAdapter(@Value("${ch.tapas.executor-2.executor-pool-url}") String executorPoolUrl) {

        completionEndpoint = URI.create(executorPoolUrl + "/completion");
    }

    public void setTaskComplete(TaskJsonRepresentation task) {

        try {
            // Assemble request
            var bodyString = om.writeValueAsString(task);


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(completionEndpoint)
                    .method("PUT", HttpRequest.BodyPublishers.ofString(bodyString))
                    .header("Content-Type", TaskJsonRepresentation.MEDIA_TYPE)
                    .build();

            // Send request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Log response
            int statusCode = response.statusCode();
            log.info("Http Request: PUT " + completionEndpoint.toString() + " " + statusCode + " " + bodyString);

            if (statusCode != 200) {

                throw new RuntimeException("Unexpected response status code: " + statusCode);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Cannot update task", ex);
        }

    }
}
