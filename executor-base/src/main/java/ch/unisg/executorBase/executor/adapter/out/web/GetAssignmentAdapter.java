package ch.unisg.executorbase.executor.adapter.out.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import ch.unisg.common.valueobject.ExecutorURI;
import ch.unisg.executorbase.executor.application.port.out.GetAssignmentPort;
import ch.unisg.executorbase.executor.domain.ExecutorType;
import ch.unisg.executorbase.executor.domain.Task;

import org.json.JSONObject;

@Component
@Primary
public class GetAssignmentAdapter implements GetAssignmentPort {

    @Value("${roster.url}")
    String server;

    Logger logger = Logger.getLogger(GetAssignmentAdapter.class.getName());

    @Override
    public Task getAssignment(ExecutorType executorType, ExecutorURI executorURI) {

        String body = new JSONObject()
                  .put("executorType", executorType)
                  .put("executorURI", executorURI.getValue())
                  .toString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server+"/task/apply"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.body().equals("")) {
                return null;
            }

            return new Task(new JSONObject(response.body()).getString("taskID"));

        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }

        return null;
    }

}
