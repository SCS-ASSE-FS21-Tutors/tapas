package ch.unisg.tapasexecutors.executors.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URL;
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
        this.executorState = new ExecutorState(State.OPEN);
        this.executorId = new ExecutorId(UUID.randomUUID().toString());
    }

    protected static RobotExecutor createExecutorWithNameAndType(ExecutorName name, ExecutorType type) {
        // for debugging
        System.out.println("New Executor: " + name.getValue() + " " + type.getValue());
        return new RobotExecutor(name,type);
    }

    @Override
    public void startTask() {
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
        System.out.println(res.toString());
    }

    public static void main(String[] args) throws IOException {
        connectRobot();
    }
}
