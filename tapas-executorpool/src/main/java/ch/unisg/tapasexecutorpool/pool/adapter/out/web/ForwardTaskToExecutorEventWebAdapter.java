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
import java.util.Locale;

@Component
@Primary
public class ForwardTaskToExecutorEventWebAdapter implements ForwardTaskToExecutorEventPort {

    @Override
    public void forwardTaskToPoolEvent(ForwardTaskToExecutorEvent event) {
        var payload = TaskMediaType.serialize(event.task);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder().uri(URI.create(event.executor.getExecutorAddress().getValue()))
                .setHeader(HttpHeaders.CONTENT_TYPE, TaskMediaType.TASK_MEDIA_TYPE)
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
