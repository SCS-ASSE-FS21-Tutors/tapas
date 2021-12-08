package ch.unisg.tapas.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Log4j2
public class CoAPRequestThread extends Thread {


    private HttpClient client;
    private ObjectMapper objectMapper;
    private String taskId;
    private String taskInput;
    private String coAPServerUri;
    private String executorPoolUri;


    public CoAPRequestThread(String taskId, String taskInput, String coAPServerUri, String executorPoolUri) {
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.taskId = taskId;
        this.taskInput = taskInput;
        this.coAPServerUri = coAPServerUri;
        this.executorPoolUri = executorPoolUri;
    }

    @Override
    public void run() {
        String endpoint = coAPServerUri + taskInput;
        log.info("Sending request to CoAP Server: " + endpoint);
        Request request = new Request(CoAP.Code.GET);
        request.setURI(endpoint);
        request.send();
        try {
            // Wait for response from the CoAP Server and parse actual result from response
            Response response = request.waitForResponse();
            String result = objectMapper.readValue(response.getPayloadString(), JsonNode.class).get("value").toString();
            log.info("CoAP Response received: " + result);

            // Send result back to executor pool
            String url = executorPoolUri + "completion";
            log.info("Sending result to Executor pool at: " + url);
            String inputDataJson = new JSONObject()
                    .put("taskId", taskId)
                    .put("outputData", result)
                    .toString();

            HttpRequest executorPoolRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .headers("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(inputDataJson))
                    .build();
            HttpResponse executorPoolResponse = client.send(executorPoolRequest, HttpResponse.BodyHandlers.ofString());
            if (executorPoolResponse.statusCode() != 200) {
                throw new RuntimeException("Executor pool responded with unexpected status code " +
                        executorPoolResponse.statusCode() + " instead of 200");
            }
        } catch (Exception e) {
            log.error("Processing CoAP Request failed");
            e.printStackTrace();
        }


    }
}
