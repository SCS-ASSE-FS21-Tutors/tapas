package ch.unisg.executorbase.executor.adapter.out.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import ch.unisg.common.valueobject.ExecutorURI;
import ch.unisg.executorbase.executor.application.port.out.NotifyExecutorPoolPort;
import ch.unisg.executorbase.executor.domain.ExecutorType;

@Component
@Primary
public class NotifyExecutorPoolAdapter implements NotifyExecutorPoolPort {

    String server = System.getenv("executor_pool_uri") == null ?
        "http://localhost:8083" : System.getenv("executor_pool_uri");

    Logger logger = Logger.getLogger(NotifyExecutorPoolAdapter.class.getName());

    /**
    *   Notifies the executor-pool about the startup of this executor
    *   @return if the notification was successful
    **/
    @Override
    public boolean notifyExecutorPool(ExecutorURI executorURI, ExecutorType executorType) {

        String body = new JSONObject()
            .put("executorTaskType", executorType)
            .put("executorUri", executorURI.getValue())
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
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }

         return false;
    }

}
