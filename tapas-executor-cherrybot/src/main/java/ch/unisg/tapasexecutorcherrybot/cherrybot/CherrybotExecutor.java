package ch.unisg.tapasexecutorcherrybot.cherrybot;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class CherrybotExecutor {
    public enum State {
        IDLE, BUSY
    }

    @Getter
    private final ExecutorName executorName = new ExecutorName("CherrybotExecutor");

    @Getter
    @Setter
    private ExecutorState executorState;

    @Getter
    private ExecutorId executorId;

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public CherrybotExecutor() {
        this.executorState = new ExecutorState(State.IDLE);
        this.executorId = new ExecutorId(UUID.randomUUID().toString());
    }

    /**
     * Sends POST /operator request to gain access to the robot
     */
    public String registerOperator() throws IOException, InterruptedException {
        String json = "{\"name\":\"Tapas Team 2\",\"email\":\"team2@unisg.ch\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create("https://api.interactions.ics.unisg.ch/cherrybot/operator"))
                .setHeader("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        Optional<String> delete_url = response.headers().firstValue("Location");
        return delete_url.get();
    }

    /**
     * Sends PUT /tcp/target to set the robot's point target to which it will move
     */
    public void makeRobotDoStuff(String token) throws IOException, InterruptedException {
        // json formatted data
        String json = "{" +
                "\"target\": {" +
                "\"coordinate\": {" +
                "\"x\": 600," +
                "\"y\": 0," +
                "\"z\": 600" +
                "}," +
                "\"rotation\": {" +
                "\"roll\": 0," +
                " \"pitch\": 0," +
                "\"yaw\": 180" +
                "}" +
                "}," +
                "\"speed\": 50" +
                "}";

        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create("https://api.interactions.ics.unisg.ch/cherrybot/tcp/target"))
                .setHeader("Content-Type", "application/json")
                .header("Authentication", token)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());
    }

    /**
     * Sends PUT /initialize to reset the robot
     */
    public void initRob(String token) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create("https://api.interactions.ics.unisg.ch/cherrybot/initialize"))
                .setHeader("Authentication", token)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());
    }

    public void getRobotStatus(String token) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.interactions.ics.unisg.ch/cherrybot/tcp"))
                .setHeader("Authentication", token)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    /**
     * Sends DELETE /operator request to delete the current operator
     * @param deleteurl
     */
    public void delete(String deleteurl) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(deleteurl))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());
    }

    public void startTask() throws IOException, InterruptedException {
        executorState = new ExecutorState(State.BUSY);
        runTask();
    }

    private void sleeper() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    public void runTask() throws IOException, InterruptedException {
        String deleteurl = registerOperator();
        sleeper();
        String token = deleteurl.substring(deleteurl.lastIndexOf("/") + 1);
        makeRobotDoStuff(token);
        sleeper();
        delete(deleteurl);
    }

    private static void getCurrent() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.interactions.ics.unisg.ch/cherrybot/operator"))
                //.setHeader("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    /**
     * Main method for quick terminal debugging purposes
     */
//    public static void main(String[] args) throws IOException, InterruptedException {
//        // getCurrent();
//        String deleteurl = registerOperator();
//        System.out.println(deleteurl);
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            // ignore
//        }
//        String token = deleteurl.substring(deleteurl.lastIndexOf("/") + 1);
//        getRobotStatus(token);
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            // ignore
//        }
//        makeRobotDoStuff(token);
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            // ignore
//        }
//        delete(deleteurl);
//    }

    @Value
    class ExecutorId {
        private String value;
    }

    @Value
    class ExecutorName {
        private String value;
    }

    @Value
    class ExecutorState {
        private CherrybotExecutor.State value;
    }

    @Value
    class ExecutorType {
        private String value;
    }
}
