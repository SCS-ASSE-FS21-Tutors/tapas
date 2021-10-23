package ch.unisg.tapasroster.roster.adapter.out.web;

import ch.unisg.tapasroster.roster.application.port.out.TaskAssignedInternallyEventPort;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class TaskAssignedInternallyEventWebAdapter implements TaskAssignedInternallyEventPort {

    @Override
    public boolean publishTaskAssignedInternallyEvent(String executorUrl) {
        URI queryUri = URI.create(executorUrl + "/executeTask/");
        // send request to executor for execution of a Task
        HttpRequest requestToExecutor = HttpRequest.newBuilder()
                .uri(queryUri)
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(requestToExecutor, HttpResponse.BodyHandlers.ofString());
            // Check whether HTTP response was successful or not
            if (response.statusCode() / 100 != 2) {
                System.err.printf("Calling %s with %s returns StatusCode: %d",
                        requestToExecutor.uri(), requestToExecutor.method(), response.statusCode());
                return false;
            }
            return true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
