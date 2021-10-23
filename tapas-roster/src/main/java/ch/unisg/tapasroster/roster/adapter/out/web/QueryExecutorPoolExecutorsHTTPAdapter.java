package ch.unisg.tapasroster.roster.adapter.out.web;

import ch.unisg.tapasroster.roster.application.port.out.QueryExecutorPoolExecutorsPort;
import ch.unisg.tapasroster.roster.domain.Executor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class QueryExecutorPoolExecutorsHTTPAdapter implements QueryExecutorPoolExecutorsPort {

    @Value("${roster.executorPool.url: url_not_found}")
    private String executorPoolUrl; // ExecutorPool URL read from application.properties


    @Override
    public Optional<List<Executor>> queryExecutorsFromExecutorPoolQuery() {
        List<Executor> parsedExecutorList = new ArrayList<>();
        // Create HTTP request
        String executorPoolURL = this.executorPoolUrl;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(executorPoolURL + "/executors/"))
                .GET()
                .build();
        try {
            // Send HTTP-Request to ExecutorPool to get a list of all executors
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() / 100 == 2) {
                try {
                    JSONObject responseJSON = new JSONObject(response.body());
                    JSONArray executorJSONArray = responseJSON.getJSONArray("executorList");
                    try {
                        // Convert response body to ExecutorList
                        for (int i = 0; i < executorJSONArray.length(); i++) {
                            JSONObject currExecutor = executorJSONArray.getJSONObject(i);
                            String executorType = currExecutor.getString("executorType");
                            String executorUrl = currExecutor.getString("executorUrl");
                            Executor executor = new Executor(
                                    new Executor.ExecutorUrl(executorUrl),
                                    new Executor.ExecutorType(executorType)
                            );
                            parsedExecutorList.add(executor);
                        }
                        return Optional.of(parsedExecutorList);
                    } catch (JSONException e) {
                        System.err.println("Error while parsing executors:");
                        System.err.println(e.getMessage());
                    }
                } catch (JSONException e) {
                    System.err.println("Could not create JSON-Object from response object from ExecutorPool");
                }
            } else {
                System.err.printf("Calling %s with method %s returns StatusCode: %d",
                        request.uri(), request.method(), response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }
}
