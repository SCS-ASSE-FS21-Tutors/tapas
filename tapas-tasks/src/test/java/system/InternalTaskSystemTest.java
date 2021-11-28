package system;

import ch.unisg.tapastasks.tasks.adapter.in.formats.TaskJsonRepresentation;
import ch.unisg.tapastasks.tasks.domain.Task;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

@Log4j2
public class InternalTaskSystemTest {

    private String taskServiceUrl = "https://tapas-tasks.86-119-34-242.nip.io/";
    private HttpClient client = HttpClient.newHttpClient();

    /**
     * This tasks needs the whole TAPAS system with
     * all microservices running. It tests if a task get executed
     * by an executor after it has been added to the task service.
     */
    @Test
    @Disabled
    public void testInternalTaskExecution() throws IOException, InterruptedException {

        // Create new Task
        Task task = createTask();
        var taskId = task.getTaskId().getValue();
        log.info("Created new task with id {} and status {}", taskId, task.getTaskStatus());

        for (int i = 0; i < 30; i++) {
            TimeUnit.SECONDS.sleep(1);

            Task taskRefreshed = readTask(taskId);
            log.info("Task: {} Status: {}", taskId, task.getTaskStatus());


            if(task.getTaskStatus().getValue() == Task.Status.EXECUTED){
                return;
            }
        }
        Assertions.fail("Task was not executed within the timelimit");
    }

    private Task readTask(String taskId) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(taskServiceUrl + "tasks/" + taskId))
            .headers("Content-Type", "application/task+json")
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200)
            throw new RuntimeException("Executor pool responded with statusCode " + response.statusCode() + " but 200 is expected");

        return TaskJsonRepresentation.deserialize(response.body());
    }

    private Task createTask() throws IOException, InterruptedException {
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

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(taskServiceUrl + "tasks/"))
            .headers("Content-Type", "application/task+json")
            .POST(HttpRequest.BodyPublishers.ofString(taskJson))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 201)
            throw new RuntimeException("Executor pool responded with statusCode " + response.statusCode() + " but 200 is expected");

        return TaskJsonRepresentation.deserialize(response.body());
    }
}
