package ch.unisg.tapasrobotexecutor.robotexecutor.adapter.out.web;

import ch.unisg.tapasrobotexecutor.robotexecutor.application.port.out.DeleteOperatorPort;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DeleteOperatorWebAdapter implements DeleteOperatorPort {

    @Override
    public void deleteOperator(String authKey) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(EndpointHandler.server + "/operator/" + authKey))
                .DELETE()
                .setHeader("accept", "*/*")
                .setHeader("Content-Type", "application/json")
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
