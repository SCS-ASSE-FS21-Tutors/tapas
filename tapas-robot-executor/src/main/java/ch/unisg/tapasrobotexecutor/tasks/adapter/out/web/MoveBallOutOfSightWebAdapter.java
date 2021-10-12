package ch.unisg.tapasrobotexecutor.tasks.adapter.out.web;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MoveBallOutOfSightWebAdapter {
    private static String retrieveTargetCoordinates() {
        JSONObject coordinate = new JSONObject();
        coordinate.put("x", 100);
        coordinate.put("y", 0);
        coordinate.put("z", 1000);

        JSONObject rotation = new JSONObject();
        rotation.put("roll", 0);
        rotation.put("pitch", 0);
        rotation.put("yaw", 0);

        JSONObject target = new JSONObject();
        target.put("coordinate", coordinate);
        target.put("rotation", rotation);

        JSONObject combinedTargetCoords = new JSONObject();
        combinedTargetCoords.put("target", target);
        combinedTargetCoords.put("speed", 100);

        return combinedTargetCoords.toString();
    }

    public static void moveBall(String authKey) {
        String requestBody = retrieveTargetCoordinates();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(EndpointHandler.server+EndpointHandler.targetEndpoint))
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
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
