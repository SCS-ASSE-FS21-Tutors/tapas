package ch.unisg.tapasrobotexecutor.robotexecutor.adapter.out.web;

import ch.unisg.tapasrobotexecutor.robotexecutor.application.port.out.RegisterNewOperatorPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class RegisterNewOperatorWebAdapter implements RegisterNewOperatorPort {
    private static final Integer waitTimeBetweenRequests = 1;

    @Override
    public Optional<String> authorizeOperator() {
        var values = new HashMap<String, String>() {{
            put("name", EndpointHandler.name);
            put("email", EndpointHandler.email);
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
                .uri(URI.create(EndpointHandler.server + EndpointHandler.operatorEndpoint))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .setHeader("accept", "*/*")
                .setHeader("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            while (response.statusCode() == 403) {
                try {
                    TimeUnit.SECONDS.sleep(waitTimeBetweenRequests);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            }
            Map<String, List<String>> map = response.headers().map();
            if (response.statusCode() != 200 || !map.keySet().contains("location")) {
                return Optional.empty();
            } else {
                String locationString = map.get("location").get(0);
                return Optional.of(locationString.substring(57));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
