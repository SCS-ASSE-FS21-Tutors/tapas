package ch.unisg.tapascommon.communication;

import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WebClient {

    public static HttpResponse<String> get(String uri, String payload, String contentType) {
        return httpRequest("POST", uri, payload, contentType);
    }

    public static HttpResponse<String> post(String uri, String payload, String contentType) {
        return httpRequest("POST", uri, payload, contentType);
    }

    public static HttpResponse<String> put(String uri, String payload, String contentType) {
        return httpRequest("PUT", uri, payload, contentType);
    }

    public static HttpResponse<String> patch(String uri, String payload, String contentType) {
        return httpRequest("PATCH", uri, payload, contentType);
    }

    public static HttpResponse<String> httpRequest(String method, String uri, String payload, String contentType) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .setHeader(HttpHeaders.CONTENT_TYPE, contentType)
                .method(method, HttpRequest.BodyPublishers.ofString(payload))
                .build();
        var client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
}
