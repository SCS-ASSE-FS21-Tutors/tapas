package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.TaskJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.port.out.TaskWonEventPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class TaskWonEventHttpAdapter implements TaskWonEventPort {
    @Value("${auctionHouse.tasks.url: url_not_found}")
    private String server;
    @Override
    public boolean addWonTaskToTaskList(TaskJsonRepresentation taskJsonRepresentation) {
        boolean success = false;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(server+"/tasks/"))
            .POST(HttpRequest.BodyPublishers.ofString(taskJsonRepresentation.toJSON()))
            .header(HttpHeaders.CONTENT_TYPE, "application/task+json")
            .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            success = true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return success;
    }
}
