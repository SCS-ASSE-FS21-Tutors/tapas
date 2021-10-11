package ch.unisg.tapasroster.roster.adapter.out.web;

import ch.unisg.tapasroster.roster.adapter.in.web.TaskMediaType;
import ch.unisg.tapasroster.roster.application.port.out.ExecuteTaskOnPoolEventPort;
import ch.unisg.tapasroster.roster.domain.ForwardTaskToPoolEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@Primary
public class ForwardTaskToPoolEventWebAdapter implements ExecuteTaskOnPoolEventPort {

    String server = "http://127.0.0.1:8083";

    @Override
    public void forwardTaskToPoolEvent(ForwardTaskToPoolEvent event) {
        var payload = TaskMediaType.serialize(event.task);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server + "/executor-pool/execute-task/"))
                .setHeader(HttpHeaders.CONTENT_TYPE, TaskMediaType.TASK_MEDIA_TYPE)
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
