package ch.unisg.tapasexecutors.executors.domain;

import ch.unisg.tapastasks.tasks.domain.Task;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RobotExecutor implements Executors {

    @Getter
    private final ExecutorName executorName = new ExecutorName("CherrybotExecutor");

    @Getter
    private final Task.TaskType taskType = new Task.TaskType("RobotTask");

    @Getter
    @Setter
    private ExecutorState executorState;

    @Getter
    private ExecutorId executorId;

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public RobotExecutor() {
        this.executorState = new ExecutorState(State.IDLE);
        this.executorId = new ExecutorId(UUID.randomUUID().toString());
    }

    /**
     * Sends POST /operator request to gain access to the robot
     */
    public static String registerOperator() throws IOException, InterruptedException {
        String json = "{\"name\":\"Tapas Team 2\",\"email\":\"team2@unisg.ch\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create("https://api.interactions.ics.unisg.ch/cherrybot/operator"))
                .setHeader("Content-Type", "application/json")
                //.header("X-API-KEY", "opensesame")
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
    public static void makeRobotDoStuff(String token) throws IOException, InterruptedException {
        // json formatted data
        String json = new StringBuilder()
                .append("{")
                .append("\"target\": {")
                .append("\"coordinate\": {")
                .append("\"x\": 600,")
                .append("\"y\": 0,")
                .append("\"z\": 600")
                .append("},")
                .append("\"rotation\": {")
                .append("\"roll\": 0,")
                .append(" \"pitch\": 0,")
                .append("\"yaw\": 180")
                .append("}")
                .append("},")
                .append("\"speed\": 50")
                .append("}").toString();

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
    public static void initRob(String token) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create("https://api.interactions.ics.unisg.ch/cherrybot/initialize"))
                .setHeader("Authentication", token)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());
    }

    public static void getRobotStatus(String token) throws IOException, InterruptedException {
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
    public static void delete(String deleteurl) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(deleteurl))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());
    }

    @Override
    public void startTask() {
        executorState = new ExecutorState(State.BUSY);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // ignore
        }
        execute();
    }

    private void sleeper() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // ignore
        }
    }
    @Override
    public void execute() {
//        String deleteurl = registerOperator();
//        sleeper();
//        String token = deleteurl.substring(deleteurl.lastIndexOf("/") + 1);
//        makeRobotDoStuff(token);
//        sleeper();
//        delete(deleteurl);
    }

    @Override
    public void completeTask() {
        // TODO
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


    /* simple getter connection to robot as example */
    private static void getCurrentOperator() throws IOException {
        URL url = new URL("https://api.interactions.ics.unisg.ch/cherrybot/operator");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        StringBuilder res = new StringBuilder();
        try (BufferedReader buf = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            for (String line; (line = buf.readLine()) != null;) {
                res.append(line);
            }
        }
        System.out.println(res);
    }

    /**
     * Main method for quick terminal debugging purposes
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // getCurrent();
        String deleteurl = registerOperator();
        System.out.println(deleteurl);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // ignore
        }
        String token = deleteurl.substring(deleteurl.lastIndexOf("/") + 1);
        getRobotStatus(token);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // ignore
        }
        makeRobotDoStuff(token);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // ignore
        }
        delete(deleteurl);
    }
}
