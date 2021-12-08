package ch.unisg.tapastasks.system;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class InternalTaskSystemTest {

    String taskServiceUrl = "https://tapas-tasks.86-119-34-242.nip.io/tasks/";

    /**
     * This tasks needs the whole TAPAS system with
     * all microservices running. It tests if a task get executed
     * by an executor after it has been added to the task service.
     */
    @Test
    @Disabled
    public void testInternalTaskExecution() throws IOException, InterruptedException {

        // ARRANGE
        String taskJson = "{\n" +
            "  \"taskId\": \"string\",\n" +
            "  \"taskName\": \"string\",\n" +
            "  \"taskType\": \"string\",\n" +
            "  \"taskStatus\": \"string\",\n" +
            "  \"originalTaskUri\": \"string\",\n" +
            "  \"serviceProvider\": \"string\",\n" +
            "  \"inputData\": \"string\",\n" +
            "  \"outputData\": \"string\"\n" +
            "}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(taskServiceUrl))
            .headers("Content-Type", "application/task+json")
            .POST(HttpRequest.BodyPublishers.ofString(taskJson))
            .build();

        // ACT
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // ASSERT
        if(response.statusCode() != 201)
            throw new RuntimeException("Executor pool responded with statusCode " + response.statusCode() + " but 200 is expected");
    }
}
