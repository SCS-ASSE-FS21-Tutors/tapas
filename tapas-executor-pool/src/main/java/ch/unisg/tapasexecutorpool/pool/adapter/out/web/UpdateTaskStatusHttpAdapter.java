package ch.unisg.tapasexecutorpool.pool.adapter.out.web;

import ch.unisg.tapasexecutorpool.pool.application.port.out.UpdateTaskStatusCommand;
import ch.unisg.tapasexecutorpool.pool.application.port.out.UpdateTaskStatusCommandPort;
import ch.unisg.tapasexecutorpool.pool.domain.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@Log4j2
public class UpdateTaskStatusHttpAdapter implements UpdateTaskStatusCommandPort {

    private final String taskListUri;
    private final ObjectMapper om;
    private final HttpClient client;

    public UpdateTaskStatusHttpAdapter(@Value("${ch.unisg.tapas.task-list-url}") String executorPoolUrl) {
        this.taskListUri = executorPoolUrl;
        this.om = new ObjectMapper();
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public boolean updateTaskStatus(UpdateTaskStatusCommand command) {
        String taskId = command.getTaskId().getValue();
        String taskStatus = command.getNewStatus().getValue().name();

        log.info("Update task " + taskId + " on task list service to " + command.getNewStatus().getValue().toString());

        try {
            JSONObject patch = new JSONObject();
            patch.put("op", "replace");
            patch.put("path", "/taskStatus");
            patch.put("value", taskStatus);

            JSONArray patches = new JSONArray();
            patches.put(patch);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(taskListUri + "tasks/" + taskId))
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