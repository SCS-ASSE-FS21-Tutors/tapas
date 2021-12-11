package ch.unisg.tapastasks.tasks.adapter.out.web;

import ch.unisg.tapastasks.tasks.adapter.in.formats.TaskJsonRepresentation;
import ch.unisg.tapastasks.tasks.application.port.out.NewTaskAddedEventPort;
import ch.unisg.tapastasks.tasks.domain.NewTaskAddedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@Primary
public class PublishNewTaskAddedEventWebAdapter implements NewTaskAddedEventPort {

    //This is the base URI of the service interested in this event (in my setup, running locally as separate Spring Boot application)
    @Value( "${ch.unisg.tapas.roster-url}" )
    private String server;

    @Override
    public void publishNewTaskAddedEvent(NewTaskAddedEvent newTaskAddedEvent) {

        //Here we would need to work with DTOs in case the payload of calls becomes more complex
        try{

            String taskJson = TaskJsonRepresentation.serialize(newTaskAddedEvent.getTask());
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(server+"roster/newtask/"))
                    .headers("Content-Type", TaskJsonRepresentation.MEDIA_TYPE)
                    .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200){
                throw new RuntimeException("Roster responded with unexpected status code " + response.statusCode() + " instead of 200");
            }

        }
        catch (Exception ex){
            throw new RuntimeException("Failed to send new Task event to roster");
        }
    }
}
