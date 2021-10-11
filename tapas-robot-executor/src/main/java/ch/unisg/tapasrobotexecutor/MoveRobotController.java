package ch.unisg.tapasrobotexecutor;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.net.http.HttpClient;


@RestController
public class MoveRobotController {

    String server = "https://api.interactions.ics.unisg.ch/cherrybot";
    String operatorEndpoint = "/operator";
    String initializeEndpoint = "/initialize";
    String targetEndpoint = "/tcp/target";

    String name = "Kay E. Jenss";
    String email = "kayerik.jenss@student.unisg.ch";

    private void authorizeOperator() {
        JSONObject payload = new JSONObject();
        payload.put("name", name);
        payload.put("email", email);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server + operatorEndpoint))
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(path = "/move")
    public ResponseEntity<String> moveBallOutOfSight() {
        authorizeOperator();

        JSONObject payload = new JSONObject();

        payload.put("operator1", "result");

        // Add the content type as a response header
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<>(payload.toString(), responseHeaders, HttpStatus.OK);
    }
}
