package ch.unisg.tapasexecutorcherrybot.cherrybot;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class CherrybotExecutorAdapter {

    private static final String SERVER = "http://127.0.0.1:8082";
    private static final String TASK_FINISHED_PATH = "/roster/taskfinished/";

    public static void publishTaskFinishedEvent(TaskFinishedEvent event) {
        String requestBody = TaskFinishedEventMediaType.serialize(event);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SERVER + TASK_FINISHED_PATH))
                .header("Content-Type", TaskFinishedEventMediaType.MEDIA_TYPE)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
