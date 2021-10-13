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

    private final String EXECUTOR_API_CALC = "http://127.0.0.1:8084/executor-calc/execute-task/";
    private final String EXECUTOR_API_ROBOT = "http://127.0.0.1:8085/executor-robot/execute-task/";

    @Override
    public void forwardTaskToPoolEvent(ForwardTaskToExecutorEvent event) {
        var payload = TaskMediaType.serialize(event.task);
        var client = HttpClient.newHttpClient();
        var requestBuilder = HttpRequest.newBuilder();

        if (event.task.getTaskType().getValue().toLowerCase(Locale.ROOT).equals("calc")) {
            requestBuilder.uri(URI.create(EXECUTOR_API_CALC));
        } else if (event.task.getTaskType().getValue().toLowerCase(Locale.ROOT).equals("robot")) {
            requestBuilder.uri(URI.create(EXECUTOR_API_ROBOT));
        }

        var request = requestBuilder.setHeader(HttpHeaders.CONTENT_TYPE, TaskMediaType.TASK_MEDIA_TYPE)
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
