package ch.unisg.executorBase.executor.adapter.out.web;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import ch.unisg.executorBase.executor.application.port.out.NotifyExecutorPoolPort;
import ch.unisg.executorBase.executor.domain.ExecutorType;

@Component
@Primary
public class NotifyExecutorPoolAdapter implements NotifyExecutorPoolPort {

    String server = "http://127.0.0.1:8083";

    @Override
    public boolean notifyExecutorPool(String ip, int port, ExecutorType executorType) {

        String body = new JSONObject()
            .put("executorType", executorType)
            .put("ip", ip)
            .put("port", port)
            .toString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server+"/executor/new/"))
                .POST(HttpRequest.BodyPublishers.ofString(body))
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
