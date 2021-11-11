package ch.unisg.tapas.roster.adapter.out.web;

import ch.unisg.tapas.roster.adapter.out.web.dto.CanExecuteDto;
import ch.unisg.tapas.roster.application.port.out.ExecutorPoolPort;
import ch.unisg.tapas.roster.entities.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ExecutorPoolWebAdapter implements ExecutorPoolPort {

    private final String executorPoolUrl;
    private final ObjectMapper om;
    private final HttpClient client;

    public ExecutorPoolWebAdapter(@Value( "${ch.unisg.tapas.executor-pool-url}" ) String executorPoolUrl) {

        this.executorPoolUrl = executorPoolUrl;
        this.om = new ObjectMapper();
        this.client = HttpClient.newHttpClient();

    }

    @Override
    public boolean canExecuteInternally(Task task) {


        try{
            // Serialize the Task object
            var taskJson = om.writeValueAsString(task);

            // Send task to executor pool
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(executorPoolUrl+"/can-execute/"))
                    .headers("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200)
                throw new RuntimeException("Executor pool responded with statusCode " + response.statusCode() + " but 200 is expected");

            var canExecute = om.readValue(response.body(), CanExecuteDto.class);

            return canExecute.isExecutable();

        }
        catch (Exception ex){
            throw new RuntimeException("Failed to send task to executor pool", ex);
        }

    }

    @Override
    public void executeInternally(Task task) {

        try{
            // Serialize the Task object
            var taskJson = om.writeValueAsString(task);

            // Send task to executor pool
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(executorPoolUrl+"/execute/"))
                    .headers("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != HttpStatus.ACCEPTED.value())
                throw new RuntimeException("Executor pool responded with statusCode " + response.statusCode() + " but "+HttpStatus.ACCEPTED.value()+" is expected");
        }
        catch (Exception ex){
            throw new RuntimeException("Failed to send task to executor pool", ex);
        }
    }
}
