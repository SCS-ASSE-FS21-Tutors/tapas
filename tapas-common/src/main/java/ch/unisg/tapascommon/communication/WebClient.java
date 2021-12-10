package ch.unisg.tapascommon.communication;

import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WebClient {

    public static HttpResponse<String> get(String uri) throws IOException, InterruptedException {
        return httpRequest("GET", uri, null, null);
    }

    public static HttpResponse<String> post(String uri, String payload, String contentType) throws IOException, InterruptedException {
        return httpRequest("POST", uri, payload, contentType);
    }

    public static HttpResponse<String> put(String uri, String payload, String contentType) throws IOException, InterruptedException {
        return httpRequest("PUT", uri, payload, contentType);
    }

    public static HttpResponse<String> patch(String uri, String payload, String contentType) throws IOException, InterruptedException {
        return httpRequest("PATCH", uri, payload, contentType);
    }

    public static HttpResponse<String> delete(String uri, String payload, String contentType) throws IOException, InterruptedException {
        return httpRequest("DELETE", uri, payload, contentType);
    }

    public static HttpResponse<String> httpRequest(String method, String uri, String payload, String contentType) throws IOException, InterruptedException {
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
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static <T> boolean checkResponseStatusCode(HttpResponse<T> response) {
        return response != null && response.statusCode() >= 200 && response.statusCode() < 300;
    }

    public static String normalizeUrl(String url) {
        final String HTTP = "http://";
        final String HTTPS = "https://";

        final String[] PROTOCOLS = new String[] { HTTP, HTTPS };

        for (var protocol : PROTOCOLS) {
            if (url.contains(protocol)) {
                return protocol + url.replace(protocol, "").replace("//", "/");
            }
        }

        return url;
    }
}
