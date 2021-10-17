package ch.unisg.tapas.roster.services;

import ch.unisg.tapas.roster.entities.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BasicRosterService implements RosterService {

    private final String executorPoolUrl;

    public BasicRosterService(String executorPoolUrl) {
        this.executorPoolUrl = executorPoolUrl;
    }

    @Override
    public void sendTaskToExecutor(Task newTask){

        if(true) // Dummy Implementation
            sendTaskToExecutorPool(newTask);
        else
            sendTaskToAuctionHouse(newTask);
    }

    private void sendTaskToExecutorPool(Task newTask) {

        try{

            // Serialize the Task object
            var taskJson = serializeTask(newTask);

            // Send task to executor pool
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(executorPoolUrl+"assignment/"))
                    .headers("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 201)
                throw new RuntimeException("Executor pool responded with statusCode " + response.statusCode() + " but 201 is expected");
        }
        catch (Exception ex){
            throw new RuntimeException("Failed to send task to executor pool", ex);
        }
    }

    private void sendTaskToAuctionHouse(Task newTask) {

        throw new UnsupportedOperationException("Not implemented yet");
    }

    protected String serializeTask(Task task) throws JsonProcessingException {

        var om = new ObjectMapper();
        return om.writeValueAsString(task);
    }
}
