package ch.unisg.tapastasks.tasks.adapter.out.web;

import ch.unisg.tapastasks.tasks.adapter.in.web.TaskMediaType;
import ch.unisg.tapastasks.tasks.application.port.out.NewTaskAddedEventPort;
import ch.unisg.tapastasks.tasks.domain.NewTaskAddedEvent;
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
public class PublishNewTaskAddedEventWebAdapter implements NewTaskAddedEventPort {

    public static final String ROSTER_SERVICE_API = "https://tapas-roster.86-119-35-199.nip.io/roster/schedule-task/";

    @Override
    public void publishNewTaskAddedEvent(NewTaskAddedEvent event) {

        var payload = TaskMediaType.serialize(event.task);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
            .uri(URI.create(ROSTER_SERVICE_API))
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
