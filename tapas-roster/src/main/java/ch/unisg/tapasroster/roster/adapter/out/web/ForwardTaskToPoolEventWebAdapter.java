package ch.unisg.tapasroster.roster.adapter.out.web;

import ch.unisg.tapascommon.ServiceApiAddresses;
import ch.unisg.tapascommon.tasks.adapter.in.formats.TaskJsonRepresentation;
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

    private static final String URL = ServiceApiAddresses.getExecutorPoolServiceApiUrl();
    private static final String PATH = "/executor-pool/execute-task/";

    @Override
    public void forwardTaskToPoolEvent(ForwardTaskToPoolEvent event) {
        try {
            var payload = TaskJsonRepresentation.serialize(event.task);
            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + PATH))
                    .setHeader(HttpHeaders.CONTENT_TYPE, TaskJsonRepresentation.MEDIA_TYPE)
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
