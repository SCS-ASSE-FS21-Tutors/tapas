package ch.unisg.tapasexecutors.executors.domain;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class RobotExecutor implements Executors {

    @Getter
    private final ExecutorName executorName;

    @Getter
    private final ExecutorType executorType;

    @Getter
    private final ExecutorState executorState;

    @Getter
    private ExecutorId executorId;


    public RobotExecutor(ExecutorName executorName, ExecutorType executorType) {
        this.executorName = executorName;
        this.executorType = executorType;
        this.executorState = new ExecutorState(State.IDLE);
        this.executorId = new ExecutorId(UUID.randomUUID().toString());
    }

    protected static RobotExecutor createExecutorWithNameAndType(ExecutorName name, ExecutorType type) {
        // for debugging
        System.out.println("New Executor: " + name.getValue() + " " + type.getValue());
        return new RobotExecutor(name,type);
    }

    public static void startExecutor() {
        try {
            URL url = new URL("https://api.interactions.ics.unisg.ch/cherrybot/operator");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);     // is this necessary?

            byte[] out = "{\"name\":\"Jan Tapas\",\"email\":\"janliam.albert@unisg.ch\"}" .getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            conn.setFixedLengthStreamingMode(length);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.connect();
            try(OutputStream os = conn.getOutputStream()) {
                os.write(out);
            }

            StringBuilder res = new StringBuilder();
            try (BufferedReader buf = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                for (String line; (line = buf.readLine()) != null;) {
                    res.append(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Probably another operator is currently using the robot");
            e.printStackTrace();
        }

        System.out.println("Connected to robot");
    }

    @Override
    public void startTask() {
        // TODO
    }

    @Override
    public void execute() {
        // TODO
    }

    @Override
    public void completeTask() {
        // TODO
    }


    /* simple getter connection to robot as example */
    private static void connectRobot() throws IOException {
        URL url = new URL("https://api.interactions.ics.unisg.ch/cherrybot/operator");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        StringBuilder res = new StringBuilder();
        try (BufferedReader buf = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            for (String line; (line = buf.readLine()) != null;) {
                res.append(line);
            }
        }
        if (res.equals("204 No Content")) {
            System.out.println("There is nobody operating the robot at this point in time.");
        } else {
            System.out.println("Current operator: " + res);
        }
    }

    /**
     * Main method for quick terminal debugging purposes
     */
    public static void main(String[] args) throws IOException {
        connectRobot();
        // startTask();
        // startExecutor();
    }
}
