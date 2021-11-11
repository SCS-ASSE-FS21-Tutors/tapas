package ch.unisg.tapastasks.tasks.adapter.out.web;

import ch.unisg.tapascommon.ServiceHostAddresses;
import ch.unisg.tapascommon.tasks.adapter.in.formats.TaskJsonRepresentation;
import ch.unisg.tapastasks.tasks.application.port.out.NewTaskAddedEventPort;
import ch.unisg.tapastasks.tasks.domain.NewTaskAddedEvent;
import ch.unisg.tapascommon.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskList;;
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

    private static final String URL = ServiceHostAddresses.getRosterServiceHostAddress();
    private static final String PATH = "/roster/schedule-task/";

    @Override
    public void publishNewTaskAddedEvent(NewTaskAddedEvent event) {

        var task = TaskList.getTapasTaskList().retrieveTaskById(new Task.TaskId(event.taskId));

        if (task.isEmpty()) {
            return;
        }

        try {
            var requestBody = TaskJsonRepresentation.serialize(task.get());
            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder()
                .uri(URI.create(URL + PATH))
                .setHeader(HttpHeaders.CONTENT_TYPE, TaskJsonRepresentation.MEDIA_TYPE)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
