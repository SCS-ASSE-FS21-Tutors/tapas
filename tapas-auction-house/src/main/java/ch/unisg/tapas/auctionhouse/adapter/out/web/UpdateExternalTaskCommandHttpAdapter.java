package ch.unisg.tapas.auctionhouse.adapter.out.web;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.TaskJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.port.out.ExecuteExternalTaskCommand;
import ch.unisg.tapas.auctionhouse.application.port.out.ExecuteExternalTaskCommandPort;
import ch.unisg.tapas.auctionhouse.application.port.out.UpdateExternalTaskCommand;
import ch.unisg.tapas.auctionhouse.application.port.out.UpdateExternalTaskCommandPort;
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
public class UpdateExternalTaskCommandHttpAdapter implements UpdateExternalTaskCommandPort {

    private HttpClient client;

    public UpdateExternalTaskCommandHttpAdapter() {
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public boolean updateExternalTask(UpdateExternalTaskCommand command) {
        log.info("Update external task object to "+command.getNewStatus().getValue().toString());
        Task task = command.getTask();
        task.setTaskStatus(command.getNewStatus());

        try {
            String taskJson = TaskJsonRepresentation.serialize(task);
            //TODO Implement Patch request
            /*HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(task.getOriginalTaskUri().getValue()))
                .headers("Content-Type", "application/json")
                .PATCH(HttpRequest.BodyPublishers.ofString(taskJson))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 202)
                throw new RuntimeException("Sending won task to executor pool resulted in code " + response.statusCode() + " but 202 is expected");
            else return true;
           */
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Could not place bid", e);
        }
    }
}
