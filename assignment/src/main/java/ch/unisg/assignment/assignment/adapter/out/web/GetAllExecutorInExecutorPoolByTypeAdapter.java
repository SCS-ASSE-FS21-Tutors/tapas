package ch.unisg.assignment.assignment.adapter.out.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import ch.unisg.assignment.assignment.application.port.out.GetAllExecutorInExecutorPoolByTypePort;
import ch.unisg.assignment.assignment.domain.valueobject.ExecutorType;

@Component
@Primary
public class GetAllExecutorInExecutorPoolByTypeAdapter implements GetAllExecutorInExecutorPoolByTypePort {

    @Override
    public boolean doesExecutorTypeExist(ExecutorType type) {
        String server = "http://127.0.0.1:8083";

        Logger logger = Logger.getLogger(PublishNewTaskEventAdapter.class.getName());


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server + "/executor-pool/GetAllExecutorInExecutorPoolByType/" + type.getValue()))
                .header("Content-Type", "application/json")
                .GET()
                .build();


        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == HttpStatus.OK.value()) {
                JSONArray jsonArray = new JSONArray(response.body().toString());
                if (jsonArray.length() > 0) {
                    return true;
                }
            }
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        }
        return false;
    }

}
