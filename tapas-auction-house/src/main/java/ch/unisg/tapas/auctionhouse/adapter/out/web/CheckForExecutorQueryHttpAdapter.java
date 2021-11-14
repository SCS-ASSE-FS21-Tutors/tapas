package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.application.handler.AuctionStartedHandler;
import ch.unisg.tapas.auctionhouse.application.port.out.CheckForExecutorQuery;
import ch.unisg.tapas.auctionhouse.application.port.out.CheckForExecutorQueryPort;
import ch.unisg.tapas.auctionhouse.domain.Task;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class CheckForExecutorQueryHttpAdapter implements CheckForExecutorQueryPort {

    private String executorPoolUrl;
    private HttpClient client;
    private ObjectMapper om;

    public CheckForExecutorQueryHttpAdapter(@Value("${ch.unisg.tapas.executor-pool-url}") String executorPoolUrl) {
        this.om = new ObjectMapper();
        this.client = HttpClient.newHttpClient();
        this.executorPoolUrl = executorPoolUrl;
    }

    @Override
    public boolean checkForExecutor(CheckForExecutorQuery query) {
        log.info("Sending request to executor pool");

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
