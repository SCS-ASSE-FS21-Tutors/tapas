package ch.unisg.tapasexecutorrobot.executor.domain;

import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CherryBot {

    private static final String ROBOT_API = "https://api.interactions.ics.unisg.ch/cherrybot/";

    private static final String OPERATOR_NAME = "Group 4";
    private static final String OPERATOR_EMAIL = "group4@unisg.ch";

    private String operator_token = "";

    public CherryBot() { }

    public boolean postOperator() {
        var json = "{\n" +
                "  \"name\": \"" + OPERATOR_NAME + "\",\n" +
                "  \"email\": \"" + OPERATOR_EMAIL + "\"" +
                "}";
        var response = sendRestRequest("operator", "post", json);

        var ok = checkResponseStatusCode(response);
        if (ok) {
            var locationSplit = response.headers().map().get("location").get(0).split("/");
            operator_token = locationSplit[locationSplit.length - 1];
            System.out.println("Token: " + operator_token);
        }

        return ok;
    }

    public boolean deleteOperator() {
        var response = sendRestRequest("operator/" + operator_token, "delete", null);
        return checkResponseStatusCode(response);
    }

    public boolean postInitialize() {
        var response = sendRestRequest("initialize", "put", "");
        return checkResponseStatusCode(response);
    }

    public boolean putTcp() {
        var json = "{\n" +
                "  \"target\": {\n" +
                "    \"coordinate\": {\n" +
                "      \"x\": 400,\n" +
                "      \"y\": 0,\n" +
                "      \"z\": 400\n" +
                "    },\n" +
                "    \"rotation\": {\n" +
                "      \"roll\": 180,\n" +
                "      \"pitch\": 0,\n" +
                "      \"yaw\": 0\n" +
                "    }\n" +
                "  },\n" +
                "  \"speed\": 50\n" +
                "}";
        var response = sendRestRequest("tcp/target", "put", json);
        return checkResponseStatusCode(response);
    }

    private boolean checkResponseStatusCode(HttpResponse<String> response) {
        var ok = response != null && response.statusCode() >= 200 && response.statusCode() < 300;

        if (ok) {
            System.out.println("REST Operation successful");
        } else {
            System.out.println("REST Operation failed");
        }

        return ok;
    }

    private HttpResponse<String> sendRestRequest(String path, String operation, String payload) {
        var client = HttpClient.newHttpClient();
        var requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(ROBOT_API + path));

        if (operation.equals("post")) {
            requestBuilder.POST(HttpRequest.BodyPublishers.ofString(payload))
                    .setHeader(HttpHeaders.CONTENT_TYPE, "application/json");;
        } else if (operation.equals("put")) {
            requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(payload))
                    .setHeader(HttpHeaders.CONTENT_TYPE, "application/json");;
        } else if (operation.equals(("delete"))) {
            requestBuilder.DELETE();
        }

        if (!operator_token.isEmpty()) {
            requestBuilder.setHeader("Authentication", operator_token);
        }

        var request = requestBuilder.build();

        HttpResponse<String> response = null;

        try {
            response =  client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

            Thread.sleep(1000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return response;
    }
}
