package ch.unisg.tapascommon.communication;

import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WebClient {

    public static HttpResponse<String> get(String uri) {
        return httpRequest("GET", uri, null, null);
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
        var builder = HttpRequest.newBuilder().uri(URI.create(uri));

        if (contentType != null) {
            builder.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
        }

        if (payload != null) {
            builder.method(method, HttpRequest.BodyPublishers.ofString(payload));
        } else {
            builder.method(method, HttpRequest.BodyPublishers.noBody());
        }

        var request = builder.build();
        var client = HttpClient.newHttpClient();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static <T> boolean checkResponseStatusCode(HttpResponse<T> response) {
        return response != null && response.statusCode() >= 200 && response.statusCode() < 300;
    }
}
