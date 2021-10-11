package ch.unisg.executor1.executor.adapter.out.web;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import ch.unisg.executor1.executor.application.port.out.GetAssignmentPort;
import ch.unisg.executor1.executor.domain.ExecutorType;
import ch.unisg.executor1.executor.domain.Task;

@Component
@Primary
public class GetAssignmentAdapter implements GetAssignmentPort {

    //This is the base URI of the service interested in this event (in my setup, running locally as separate Spring Boot application)
    String server = "http://127.0.0.1:8082";

    @Override
    public Task getAssignment(ExecutorType executorType) {
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server+"/assignment/" + executorType))
                .GET()
                .build();

        /** Needs the other service running
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         **/

         // TODO return null or a new Task here depending on the response of the http call

         return null;
    }
    
}
