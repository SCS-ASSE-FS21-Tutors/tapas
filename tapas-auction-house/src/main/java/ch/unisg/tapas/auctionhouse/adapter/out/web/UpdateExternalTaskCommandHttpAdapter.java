package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.TaskJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.port.out.ExecuteExternalTaskCommand;
import ch.unisg.tapas.auctionhouse.application.port.out.ExecuteExternalTaskCommandPort;
import ch.unisg.tapas.auctionhouse.application.port.out.UpdateExternalTaskCommand;
import ch.unisg.tapas.auctionhouse.application.port.out.UpdateExternalTaskCommandPort;
import ch.unisg.tapas.auctionhouse.domain.Task;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@Primary
@Log4j2
public class UpdateExternalTaskCommandHttpAdapter implements UpdateExternalTaskCommandPort {

    private HttpClient client;

    public UpdateExternalTaskCommandHttpAdapter() {
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public boolean updateExternalTask(UpdateExternalTaskCommand command) {
        String taskListUri = command.getTask().getOriginalTaskUri().getValue();
        String taskId = command.getTask().getTaskId().getValue();
        String taskStatus = command.getNewStatus().getValue().name();

        log.info("Update external task " + taskId + " on task list service to " + command.getNewStatus().getValue().name());

        try {
            JSONObject patch = new JSONObject();
            patch.put("op", "replace");
            patch.put("path", "/taskStatus");
            patch.put("value", taskStatus);

            JSONArray patches = new JSONArray();
            patches.put(patch);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(taskListUri))
                .headers("Content-Type", "application/json-patch+json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(patches.toString()))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200)
                throw new RuntimeException("Sending task patch to task list service resulted in code " +
                    response.statusCode() + " but 200 is expected");
            else return true;

        } catch (Exception e) {
            throw new RuntimeException("Could not update task status", e);
        }

    }
}
