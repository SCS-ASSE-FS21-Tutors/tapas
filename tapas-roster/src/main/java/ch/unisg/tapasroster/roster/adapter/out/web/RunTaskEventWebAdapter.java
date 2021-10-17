package ch.unisg.tapasroster.roster.adapter.out.web;

import ch.unisg.tapasroster.roster.application.port.out.RunTaskEventPort;
import ch.unisg.tapasroster.roster.domain.RunTaskEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@Primary
public class RunTaskEventWebAdapter implements RunTaskEventPort {

    @Override
    public void runTaskEvent(RunTaskEvent event) {

        //Here we would need to work with DTOs in case the payload of calls becomes more complex
        String requestBody = RunTaskEventMediaType.serialize(event);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(event.getURI()))
                .header("Content-Type", RunTaskEventMediaType.MEDIA_TYPE)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();


        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
