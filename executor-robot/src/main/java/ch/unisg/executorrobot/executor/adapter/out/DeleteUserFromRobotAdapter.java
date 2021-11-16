package ch.unisg.executorrobot.executor.adapter.out;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import ch.unisg.executorrobot.executor.application.port.out.DeleteUserFromRobotPort;

@Component
@Primary
public class DeleteUserFromRobotAdapter implements DeleteUserFromRobotPort {

    @Override
    public boolean deleteUserFromRobot(String key) {

        String url = "https://api.interactions.ics.unisg.ch/leubot1/v1.3.0/user/" + key;
  
        var request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .DELETE()
            .build();
 
        var client = HttpClient.newHttpClient();
 
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
