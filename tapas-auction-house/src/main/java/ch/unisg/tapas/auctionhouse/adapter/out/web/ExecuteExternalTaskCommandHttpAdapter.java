package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.TaskJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.port.out.ExecuteExternalTaskCommand;
import ch.unisg.tapas.auctionhouse.application.port.out.ExecuteExternalTaskCommandPort;
import ch.unisg.tapas.auctionhouse.domain.Task;
import lombok.extern.log4j.Log4j2;
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
public class ExecuteExternalTaskCommandHttpAdapter implements ExecuteExternalTaskCommandPort {

    private HttpClient client;
    private String executorPoolUrl;

    public ExecuteExternalTaskCommandHttpAdapter(@Value("${ch.unisg.tapas.executor-pool-url}") String executorPoolUrl) {
        this.client = HttpClient.newHttpClient();
        this.executorPoolUrl = executorPoolUrl;
    }

    @Override
    public boolean executeExternalTask(ExecuteExternalTaskCommand command) {
        log.info("Send won Task to internal Executor Pool");
        Task task = command.getTask();

        try {
            String taskJson = TaskJsonRepresentation.serialize(task);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(executorPoolUrl + "execute?external=true"))
                .headers("Content-Type", TaskJsonRepresentation.MEDIA_TYPE)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 202)
                throw new RuntimeException("Sending won task to executor pool resulted in code " + response.statusCode() + " but 202 is expected");
            else return true;
        } catch (Exception e) {
            throw new RuntimeException("Could not place bid", e);
        }
    }
}
