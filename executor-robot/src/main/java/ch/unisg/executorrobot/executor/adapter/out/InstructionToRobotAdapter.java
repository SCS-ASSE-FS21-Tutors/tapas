package ch.unisg.executorrobot.executor.adapter.out;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import ch.unisg.executorrobot.executor.application.port.out.InstructionToRobotPort;

@Component
@Primary
public class InstructionToRobotAdapter implements InstructionToRobotPort {

    @Override
    public boolean instructionToRobot(String key) {
        
        String putEndpoint = "https://api.interactions.ics.unisg.ch/leubot1/v1.3.0/elbow";
 
        String inputJson = "{ \"value\": 400}";
        var request = HttpRequest.newBuilder()
            .uri(URI.create(putEndpoint))
            .header("Content-Type", "application/json")
            .header("X-API-KEY", key)
            .PUT(HttpRequest.BodyPublishers.ofString(inputJson))
            .build();
 
        var client = HttpClient.newHttpClient();
 
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.headers());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;

    }
    
}