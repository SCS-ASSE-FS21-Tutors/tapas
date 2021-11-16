package ch.unisg.executorrobot.executor.adapter.out;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import ch.unisg.executorrobot.executor.application.port.out.UserToRobotPort;

@Component
@Primary
public class UserToRobotAdapter implements UserToRobotPort {

    @Override
    public String userToRobot() {
        String postEndpoint = "https://api.interactions.ics.unisg.ch/leubot1/v1.3.0/user";
 
        String inputJson = "{ \"name\":\"keanu rahimian\", \"email\":\"keanu.rahimian@student.unisg.ch\"}";
        var request = HttpRequest.newBuilder()
            .uri(URI.create(postEndpoint))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(inputJson))
            .build();
 
        var client = HttpClient.newHttpClient();
        
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.headers());
            String url = response.headers().map().get("location").toString();
            String key = url.split("/")[url.split("/").length-1].split("]")[0];
            return key;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
        
    }
    
}
