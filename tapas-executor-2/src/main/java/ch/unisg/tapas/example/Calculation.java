package ch.unisg.tapas.example;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;


@Getter
@Setter
@RequiredArgsConstructor
public class Calculation {

    private String executorPoolUri = "http://tapas-executor-pool:8083/";

    @NonNull
    private String taskId;

    @NonNull
    private String inputData;

    public int execute(){
        int result = 0;
        System.out.println("Received Input Data: "+ inputData);

        try {

            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("js");

            result = (int) engine.eval(inputData);
            TimeUnit.SECONDS.sleep(10);
            // Calls the /completion/ endpoint of the executor pool
            HttpClient client = HttpClient.newHttpClient();
            String url = executorPoolUri+ "completion";
            String inputDataJson = new JSONObject()
                    .put("taskId", taskId)
                    .put("outputData", String.valueOf(result))
                    .toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .headers("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(inputDataJson))
                    .build();

            java.net.http.HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
        }catch (Exception e){
            System.out.println(e);
        }

        return result;

    }

}
