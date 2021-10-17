package ch.unisg.executorBase.executor.adapter.out.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import ch.unisg.executorBase.executor.application.port.out.NotifyExecutorPoolPort;
import ch.unisg.executorBase.executor.domain.ExecutorType;

@Component
@Primary
public class NotifyExecutorPoolAdapter implements NotifyExecutorPoolPort {

    String server = "http://127.0.0.1:8083";

    Logger logger = Logger.getLogger(NotifyExecutorPoolAdapter.class.getName());

    @Override
    public boolean notifyExecutorPool(String ip, int port, ExecutorType executorType) {

        String body = new JSONObject()
            .put("executorTaskType", executorType)
            .put("executorIp", ip)
            .put("executorPort", Integer.toString(port))
            .toString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server+"/executor-pool/AddExecutor"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == HttpStatus.CREATED.value()) {
                return true;
            }
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        }

         return false;
    }

}
