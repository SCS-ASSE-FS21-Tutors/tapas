package ch.unisg.tapasrobotexecutor.tasks.adapter.out.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class InitializeRobotPositionWebAdapter {
    
    public static void backToInitialPosition(String authKey) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(EndpointHandler.server+EndpointHandler.initializeEndpoint))
                .PUT(HttpRequest.BodyPublishers.ofString("requestBody"))
                .setHeader("accept", "*/*")
                .setHeader("Content-Type", "application/json")
                .setHeader("Authentication", authKey)
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
