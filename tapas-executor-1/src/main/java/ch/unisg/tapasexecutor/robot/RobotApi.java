package ch.unisg.tapasexecutor.robot;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class RobotApi implements Closeable {

    private String robotUri;
    private HttpClient client = HttpClient.newHttpClient();
    private String apiKey;
    private String apiKeyDeleteEndpoint;
    private ObjectMapper om = new ObjectMapper();

    public RobotApi(String robotUri) {
        this.robotUri = robotUri;
    }

    public static RobotApi open(String robotUrl) throws Exception{

        var api = new RobotApi(robotUrl);
        api.createUser();
        return api;
    }

    private HttpResponse<String> makeRequest(String method, String endpoint, Map<String, Object> body) throws Exception{

        String bodyString = "";

        // Create http request builder
        var builder = HttpRequest.newBuilder().uri(URI.create(this.robotUri + endpoint));

        // Add body if we have one
        if(body == null){
            builder = builder.method(method, HttpRequest.BodyPublishers.noBody());
        }
        else{
            bodyString = om.writeValueAsString(body);
            builder = builder.header("Content-Type", "application/json");
            builder = builder.method(method, HttpRequest.BodyPublishers.ofString(bodyString));
        }

        // Add api key if we have one
        if(apiKey != null)
            builder = builder.header("X-API-KEY", apiKey);

        // Build and send request
        var request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Log response
        int statusCode = response.statusCode();
        log.info("Http Request: " + method + " " + endpoint + " " + statusCode + " " + bodyString);

        // Check if ok
        if(statusCode >= 300 || statusCode < 200)
            throw new RuntimeException("Unexpected status code: " + statusCode);

        return response;
    }

    private HttpResponse<String> moveRobot(String endpoint, int value) throws Exception {

        var body = new HashMap<String, Object>();
        body.put("value", value);

        return makeRequest("PUT", endpoint, body);
    }

    private void createUser() throws Exception {

        var body = new HashMap();
        body.put("name", "Iori Mizutani");
        body.put("email", "iori.mizutani@unisg.ch");

        var response = makeRequest("POST", "/user", body);

        var location = response.headers().firstValue("Location");
        this.apiKeyDeleteEndpoint = location.get();
        var idx = apiKeyDeleteEndpoint.lastIndexOf("/");
        this.apiKey = apiKeyDeleteEndpoint.substring(idx+1);
    }

    public void dance() throws Exception {

        moveRobot("/elbow", 400);
        Thread.sleep(500);

        moveRobot("/elbow", 650);
        Thread.sleep(500);

        moveRobot("/elbow", 400);
        Thread.sleep(500);

        moveRobot("/elbow", 650);
        Thread.sleep(500);

        moveRobot("/reset", 0);
    }

    @Override
    public void close() throws IOException {

        try{
            if(this.apiKeyDeleteEndpoint != null)
                makeRequest("DELETE", this.apiKeyDeleteEndpoint, null);
        }
        catch (Exception exception){
            throw new IOException(exception);
        }

    }

}
