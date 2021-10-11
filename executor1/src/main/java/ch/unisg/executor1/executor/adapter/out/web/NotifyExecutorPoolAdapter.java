package ch.unisg.executor1.executor.adapter.out.web;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import ch.unisg.executor1.executor.application.port.out.NotifyExecutorPoolPort;

@Component
@Primary
public class NotifyExecutorPoolAdapter implements NotifyExecutorPoolPort {
    
    //This is the base URI of the service interested in this event (in my setup, running locally as separate Spring Boot application)
    String server = "http://127.0.0.1:8083";

    @Override
    public boolean notifyExecutorPool(String ip, int port, String executorType) {

        var values = new HashMap<String, String>() {{
            put("ip", ip);
            put("port", Integer.toString(port));
            put("executorType", executorType);
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
                .uri(URI.create(server+"/executor/new/"))
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

         // TODO return true or false depending on result of http request;

         return true;
    }

}
