package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.application.handler.AuctionStartedHandler;
import ch.unisg.tapas.auctionhouse.application.port.out.CheckForExecutorQuery;
import ch.unisg.tapas.auctionhouse.application.port.out.CheckForExecutorQueryPort;
import ch.unisg.tapas.auctionhouse.domain.Task;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@Primary
public class CheckForExecutorQueryHttpAdapter implements CheckForExecutorQueryPort {
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(AuctionStartedHandler.class);

    @Value("${ch.unisg.tapas.executor-pool-url}")
    private String executorPoolUrl;

    private HttpClient client;
    private ObjectMapper om;

    public CheckForExecutorQueryHttpAdapter() {
        this.om = new ObjectMapper();
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public boolean checkForExecutor(CheckForExecutorQuery query) {
        LOGGER.info("Sending request to executor pool");

        Task task = new Task(new Task.TaskName("Tasktype wrapper"), new Task.TaskType(query.getAuction().getTaskType().getValue()));

        try {
            String taskJson = om.writeValueAsString(task);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(executorPoolUrl + "/can-execute/"))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() != 200)
                throw new RuntimeException("Executor pool responded with statusCode " + response.statusCode() + " but 200 is expected");

            JsonNode jsonNode = om.readTree(response.body());
            boolean canExecute = jsonNode.get("executable").asBoolean();

            return canExecute;

        } catch (Exception e) {

            throw new RuntimeException("Could not call Execuptor Pool for can execute", e);
        }

    }

}
