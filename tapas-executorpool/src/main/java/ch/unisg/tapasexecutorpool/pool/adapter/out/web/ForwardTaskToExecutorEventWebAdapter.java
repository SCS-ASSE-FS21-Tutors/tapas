package ch.unisg.tapasexecutorpool.pool.adapter.out.web;

import ch.unisg.tapasexecutorpool.pool.adapter.in.web.TaskMediaType;
import ch.unisg.tapasexecutorpool.pool.application.port.out.ForwardTaskToExecutorEventPort;
import ch.unisg.tapasexecutorpool.pool.domain.ForwardTaskToExecutorEvent;
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
public class ForwardTaskToExecutorEventWebAdapter implements ForwardTaskToExecutorEventPort {

    String server = "http://127.0.0.1:8084";

    @Override
    public void forwardTaskToPoolEvent(ForwardTaskToExecutorEvent event) {
        var payload = TaskMediaType.serialize(event.task);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server + "/executor/execute-task/"))
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
