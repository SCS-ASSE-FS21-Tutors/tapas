package ch.unisg.tapasexecutorpool.pool.adapter.web;

import ch.unisg.tapasexecutorpool.pool.application.port.out.NewExecutorAddedEventPort;
import ch.unisg.tapasexecutorpool.pool.domain.NewExecutorAddedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.HashMap;

@Component
@Primary
public class PublishNewExecutorAddedEventWebAdapter implements NewExecutorAddedEventPort {

    String server = "http://127.0.0.1:8082";

    @Override
    public void publishNewExecutorAddedEvent(NewExecutorAddedEvent event) {
        var values = new HashMap<String, String>() {{
            put("executorname",event.executorName);
            put("executorpool",event.executorPoolName);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server+"/roster/newexecutor/"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        /** Needs the other service running
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         **/
    }
}
