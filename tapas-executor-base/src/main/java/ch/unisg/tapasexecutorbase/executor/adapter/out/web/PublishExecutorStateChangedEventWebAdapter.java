package ch.unisg.tapasexecutorbase.executor.adapter.out.web;

import ch.unisg.tapascommon.ServiceHostAddresses;
import ch.unisg.tapasexecutorbase.executor.application.port.out.ExecutorStateChangedEventPort;
import ch.unisg.tapasexecutorbase.executor.domain.ExecutorStateChangedEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Primary
@Component
public class PublishExecutorStateChangedEventWebAdapter implements ExecutorStateChangedEventPort {

    private static final String URL = ServiceHostAddresses.getExecutorPoolServiceHostAddress();
    private static final String PATH = "/executors/";

    @Override
    public void publishExecutorStateChangedEvent(ExecutorStateChangedEvent event) {
        var path = PATH + "/" + event.getId() + "/" + event.getState();
        try {
            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder().uri(URI.create(URL + path))
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
