package ch.unisg.assignment.assignment.adapter.out.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import ch.unisg.assignment.assignment.application.port.out.GetAllExecutorInExecutorPoolByTypePort;
import ch.unisg.assignment.assignment.domain.valueobject.ExecutorType;

@Component
@Primary
public class GetAllExecutorInExecutorPoolByTypeAdapter implements GetAllExecutorInExecutorPoolByTypePort {

    @Value("${executor-pool.url}")
    private String server;

    @Override
    public boolean doesExecutorTypeExist(ExecutorType type) {

        Logger logger = Logger.getLogger(PublishNewTaskEventAdapter.class.getName());


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server + "/executor-pool/GetAllExecutorInExecutorPoolByType/" + type.getValue()))
                .header("Content-Type", "application/json")
                .GET()
                .build();


        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == HttpStatus.OK.value()) {
                JSONArray jsonArray = new JSONArray(response.body());
                if (jsonArray.length() > 0) {
                    return true;
                }
            }
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return false;
    }

}
